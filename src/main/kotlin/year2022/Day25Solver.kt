package year2022

import model.DaySolver

class Day25Solver : DaySolver<List<String>, String> {

    private val symbols = listOf(
        '=', '-', '0', '1', '2'
    )

    private fun convert(number: String): Long {
        var start = 1L
        var i = number.length - 1
        var result = 0L
        while (i >= 0) {
            result += (symbols.indexOf(number[i]) - 2) * start
            start *= 5
            i--
        }
        return result
    }

    private fun convert(number: Long): String {
        var n = number
        var result = ""
        while (n > 0){
            val x = (n+2)%5
            n = (n+2)/5
            result = symbols[x.toInt()] + result
        }
        return result
    }

    override fun part1(data: List<String>): String {
        return convert(data.sumOf {
            convert(it)
        })
    }

    override fun part2(data: List<String>): String {
        return ""
    }

}