package day1

import model.DaySolver

class Day1Solver : DaySolver<List<Int>, Int> {
    override fun part1(data: List<Int>): Int {
        var previous = 100000
        var count = 0
        data.forEach { x ->
            if (x > previous) {
                count++
            }
            previous = x
        }
        return count
    }

    override fun part2(data: List<Int>): Int {
        var count = 0
        var index = 0
        while (index < data.size - 3){
            if (data[index] < data[index+3]) count++
            index++
        }
        return count
    }
}