package year2021.day12

import model.DaySolver

class Day12Solver : DaySolver<List<String>, Int> {

    private fun parseData(data: List<String>): MutableMap<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()
        data.forEach {
            val line = it.split("-").map { it.trim() }.filter { it.isNotBlank() }
            result[line[0]] = result.getOrDefault(line[0], emptyList()).plus(line[1])
            result[line[1]] = result.getOrDefault(line[1], emptyList()).plus(line[0])
        }
        return result
    }

    override fun part1(data: List<String>): Int {
        return paths(parseData(data), "start")
    }

    private fun paths(data: MutableMap<String, List<String>>, point:String, path: List<String> = emptyList()): Int {
        val points = data[point]!!
        val updatedPath = path.plus(point)
        var result = 0
        points.forEach {
            if (it == "end") {
                result++
            }
            else if (it != "start" && (path.isEmpty() ||
                        smallCharacterRule1(updatedPath, it)
                        )) {
                result += paths(data, it, path.plus(point))
            }
        }
        return result
    }


    private fun updatedPaths(data: MutableMap<String, List<String>>, point:String, path: List<String> = emptyList()): Int {
        val points = data[point]!!
        val updatedPath = path.plus(point)
        var result = 0
        points.forEach {
            if (it == "end") {
                result++
            }
            else if (it != "start" && (path.isEmpty() || smallCharacterRule(updatedPath, it))) {
                result += updatedPaths(data, it, path.plus(point))
            }
        }
        return result
    }

    private fun smallCharacterRule1(path: List<String>, point: String): Boolean {

        val updatedPath = path.plus(point)
        val filteredData = updatedPath.filter {
            it != "start" && it != "end" && it.all { c -> c.isLowerCase() }
        }.fold(emptyList<String>()) { acc,ele ->
            if (acc.contains(ele)) acc else acc.plus(ele)
        }
        val result = point.all { c -> c.isUpperCase() } || !filteredData.any {word1 ->
            updatedPath.count{word2 -> word1 == word2} > 1
        }
        return result
    }

    private fun smallCharacterRule(path: List<String>, point: String): Boolean {

        val updatedPath = path.plus(point)
        val filteredData = updatedPath.filter {
            it != "start" && it != "end" && it.all { c -> c.isLowerCase() }
        }.fold(emptyList<String>()) { acc,ele ->
            if (acc.contains(ele)) acc else acc.plus(ele)
        }
        val result = point.all { c -> c.isUpperCase() } || (filteredData.count { word1 ->
            val repeatCount = updatedPath.count {word2 -> word1 == word2}
            repeatCount == 2
        } <= 1 && !filteredData.any {word1 ->
            updatedPath.count{word2 -> word1 == word2} > 2
        })
        return result
    }

    override fun part2(data: List<String>): Int {
        return updatedPaths(parseData(data), "start")
    }
}