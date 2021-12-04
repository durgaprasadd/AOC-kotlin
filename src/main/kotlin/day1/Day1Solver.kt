package day1

import model.DaySolver

class Day1Solver : DaySolver<List<Int>, Int> {
    override fun part1(data: List<Int>): Int {
        return data.windowed(2)
            .count {(x,y) -> y > x }
    }

    override fun part2(data: List<Int>): Int {
        return data.windowed(3)
            .windowed(2)
            .count {(x,y) -> y.sum() > x.sum() }
    }
}