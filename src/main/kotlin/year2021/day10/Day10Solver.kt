package year2021.day10

import model.DaySolver

class Day10Solver : DaySolver<List<String>, Long> {

    private fun parseData(data: List<String>): List<List<String>> {
        return data.map {
            it.split("").map { it.trim() }.filter { it.isNotBlank() }
        }
    }

    override fun part1(data: List<String>): Long {
        return count(parseData(data))
    }

    private fun allPoints(data: List<List<Int>>, point: Pair<Int,Int>): List<Pair<Int,Int>> {
        val points = listOf(
            Pair(-1,0),
            Pair(1,0),
            Pair(0,1),
            Pair(0,-1)
        )
        return  points.map { (x, y) -> Pair(point.first + x, point.second + y) }.filter { (x, y) ->
            x >= 0 && x < data.size && y >= 0 && y < data.first().size
        }
    }

    private fun count(data: List<List<String>>): Long {
        var result = 0L
        val openSymbols = listOf<String>("(", "[","{","<")
        val closeSymbols = listOf<String>(")", "]","}",">")
        val values = listOf<Long>(3,57,1197,25137)
        data.forEach { line ->
            val symbols = mutableListOf<String>()
            for (symbol in line) {
                when {
                    openSymbols.contains(symbol) -> symbols.add(symbol)
                    closeSymbols.contains(symbol) -> {
                        val openSymbol = openSymbols[closeSymbols.indexOf(symbol)]
                        if (symbols.last() == openSymbol){
                            symbols.removeLast()
                        }else {
                            result+= values[closeSymbols.indexOf(symbol)]
                            break
                        }
                    }
                }
            }
        }
        return result
    }

    private fun count1(data: List<List<String>>): Long {
        val result = mutableListOf<Long>()
        val openSymbols = listOf<String>("(", "[","{","<")
        val closeSymbols = listOf<String>(")", "]","}",">")
        val values = listOf<Long>(1,2,3,4)
        val requiredData = data.filter {line ->
            val symbols = mutableListOf<String>()
            var isValid = true
            for (symbol in line) {
                when {
                    openSymbols.contains(symbol) -> symbols.add(symbol)
                    closeSymbols.contains(symbol) -> {
                        val openSymbol = openSymbols[closeSymbols.indexOf(symbol)]
                        if (symbols.last() == openSymbol){
                            symbols.removeLast()
                        }else {
                            isValid = false
                            break
                        }
                    }
                }
            }
            isValid
        }

        requiredData.forEach {line ->
            val symbols = mutableListOf<String>()
            for (symbol in line) {
                when {
                    openSymbols.contains(symbol) -> symbols.add(symbol)
                    closeSymbols.contains(symbol) -> {
                        val openSymbol = openSymbols[closeSymbols.indexOf(symbol)]
                        if (symbols.last() == openSymbol){
                            symbols.removeLast()
                        }
                    }
                }
            }
            val output = symbols.map { symbol -> closeSymbols[openSymbols.indexOf(symbol)] }.reversed()
            var count = 0L
            for (symbol in output){
                count = (count * 5) + values[closeSymbols.indexOf(symbol)]
            }
            result.add(count)
        }
        result.sort()
        return result[result.size/2]
    }

    private fun basinSize(data: List<List<Int>>, point: Pair<Int, Int>, points: List<Pair<Int,Int>> = emptyList()): List<Pair<Int, Int>> {
        if (points.contains(point)){
            return emptyList()
        }
        val coOrds = allPoints(data,point)
        var result = points.plus(point)
        for (coOrd in coOrds){
            if (result.contains(coOrd) || data[coOrd.first][coOrd.second] == 9){
                continue
            }
            val temp = basinSize(data, coOrd, result)
            result = result.plus(temp.filter { x -> !result.contains(x) })
        }
        return result
    }

    override fun part2(data: List<String>): Long {
        return count1(parseData(data))
    }
}