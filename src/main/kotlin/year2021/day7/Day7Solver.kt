package year2021.day7

import model.DaySolver
import kotlin.math.abs

class Day7Solver : DaySolver<String, Int> {

    override fun part1(data: String): Int {
        val parsedData = data.split(",").filter { it.isNotBlank() }.map { it.toInt() }
        val max = parsedData.maxOrNull()!!
        var result: Int? = null
        for (i in 0..max) {
            val difference = parsedData.sumOf { x -> abs(x - i) }
            if (result == null || difference < result)
                result = difference
        }
        return result ?: 0
    }

    private fun totalSum(n: Int): Int {
        return n * (n + 1)/2
    }
    override fun part2(data: String): Int {
        val parsedData = data.split(",").filter { it.isNotBlank() }.map { it.toInt() }
        val max = parsedData.maxOrNull()!!
        var result: Int? = null
        for (i in 0..max) {
            val difference = parsedData.sumOf { x -> totalSum(abs(x - i)) }
            if (result == null || difference < result)
                result = difference
        }
        return result ?: 0
    }
}