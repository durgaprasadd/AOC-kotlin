package day3

import model.DaySolver

class Day3Solver : DaySolver<List<String>, Int> {
    override fun part1(data: List<String>): Int {
        val gammaRate = data.first().indices.map {  index ->
            val oneCount = data.sumOf {num -> num[index].toString().toInt() }
            if (data.size/2 > oneCount) {
                '0'
            }else {
                '1'
            }
        }
        val epsilonRate = gammaRate.map {
            when(it) {
                '0' -> '1'
                else -> '0'
            }
        }
        return gammaRate.joinToString("").toInt(2) * epsilonRate.joinToString("").toInt(2)
    }

    override fun part2(data: List<String>): Int {
        var filteredData = data
        var index = 0
        while (filteredData.size != 1){
            val groupedData = filteredData.groupBy {num -> num[index]}
            filteredData = if (groupedData['0']!!.size > groupedData['1']!!.size ) groupedData['0']!! else groupedData['1']!!
            index++
        }
        val oxygenGeneratorRating = filteredData.first()
        filteredData = data
        index = 0
        while (filteredData.size != 1){
            val groupedData = filteredData.groupBy {num -> num[index]}
            filteredData = if (groupedData['1']!!.size < groupedData['0']!!.size ) groupedData['1']!! else groupedData['0']!!
            index++
        }
        val cO2scrubberRating = filteredData.first()
        return oxygenGeneratorRating.toInt(2)*cO2scrubberRating.toInt(2)
    }
}