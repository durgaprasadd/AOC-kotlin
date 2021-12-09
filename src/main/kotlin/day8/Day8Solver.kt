package day8

import model.DaySolver

class Day8Solver : DaySolver<List<String>, Int> {

    private fun parseData(data: List<String>): List<Pair<List<String>, List<String>>> {
        return data.map {
            val result = it.split("|").map { it.split(" ").filter { it.isNotBlank() } }
            result[0] to result[1]
        }
    }

    override fun part1(data: List<String>): Int {
        return parseData(data).sumOf { countOutComes(it) }
    }

    private fun countOutComes(rawData: Pair<List<String>, List<String>>): Int {
        val output = rawData.second
        val possibleCombinations = listOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg")
        return output.count { x -> listOf(possibleCombinations[1],possibleCombinations[4], possibleCombinations[7], possibleCombinations[8]).any{y -> x.length == y.length} }
    }

    override fun part2(data: List<String>): Int {
        return parseData(data).sumOf { countOutComes2(it) }
    }

    private fun countOutComes2(rawData: Pair<List<String>, List<String>>): Int {
        val parsedData = rawData.first
        val output = rawData.second
        val possibleCombinations =
            listOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg")
        var allCombinations = mutableMapOf<String, List<String>>()
        parsedData.forEach { x ->
            val filteredData = possibleCombinations.filter { y -> x.length == y.length }
            val result = allCombinations.get(x)
            if (result == null || result.first().length > filteredData.first().length) {
                allCombinations[x] = filteredData
            }
        }
        var flag = false
        while (!flag) {
            val newCombinations = allCombinations.toMap().toMutableMap()
            for (key in allCombinations.keys) {
                val something = allCombinations.get(key)
                if (something == null || something.size > 1) {
                    continue
                }
                val value = something.first()
                newCombinations.keys.filter { newKey -> newKey != key && key.all { x -> newKey.contains(x) } }
                    .forEach { newKey ->
                        val newValue = newCombinations.getValue(newKey)
                        newCombinations.remove(newKey)
                        val updatedKey = newKey.filter { x ->
                            !key.contains(x)
                        }
                        val updatedValue = newValue
                            .filter { x ->
                                value.all { y -> x.contains(y) }
                            }
                            .map { x -> x.filter { y -> !value.contains(y) } }
                        newCombinations[updatedKey] = updatedValue
                    }
            }
            flag = allCombinations.values.sumOf { it.size } == newCombinations.values.sumOf { it.size }
            allCombinations = newCombinations
        }
        val newCombinations = allCombinations.toMap().toMutableMap()
        for ((key,value)in allCombinations) {
            if (key.length == 1 && value.size == 1 && value.first().length == 1){
                newCombinations.forEach { newKey, newValue ->
                    if (newKey != key) {
                        val updatedValue = newValue
                            .filter { x -> x != value.first() }
                        newCombinations[newKey] = updatedValue
                    }
                }
                continue
            }
        }
        println(newCombinations)
        println(output)
        return output.map { x ->
            x.map { y -> newCombinations[y.toString()]!!.first() }
                .joinToString("")
        }.map {
            getSome(possibleCombinations, it)
        }.joinToString("").toInt()
    }

    private fun getSome(possible:List<String>, value: String): Int {
        for (i in possible.indices) {
            val newValue = possible[i]
            if (newValue.length == value.length && newValue.all { x -> value.contains(x) } && value.all { x -> newValue.contains(x) }){
                return i
            }
        }
        return 0
    }
}