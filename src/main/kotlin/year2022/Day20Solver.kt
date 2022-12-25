package year2022

import model.DaySolver

class Day20Solver: DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<Int> {
        return data.map {
            it.toInt()
        }
    }
    override fun part1(data: List<String>): Int {
        val input = parse(data)
        val series = input.indices.toMutableList()
        var max = 0
        var min = input.minOrNull()!!
        while (max+min <= 0){
            max += input.size
        }
        println(series)
        println(series)
        println(series.take(2) + listOf(1) + series.takeLast(series.size-2))
        var i = 0
        while (i < input.size){
            val curr = series.indexOf(i)
            val newIndex = (max + input[i])%input.size
            if (curr == newIndex) {
                i++
                continue
            }
            if (input[i] > 0){
                forward(series, curr, newIndex)
            }else {
                backward(series, curr, newIndex)
            }
            i++
        }
        println(input.size - input.toSet().size)
        return 0
    }

    private fun backward(series: MutableList<Int>, curr: Int, newIndex: Int) {

    }

    private fun forward(series: MutableList<Int>, curr: Int, newIndex: Int) {

    }

    override fun part2(data: List<String>): Int {
        return 0
    }

}