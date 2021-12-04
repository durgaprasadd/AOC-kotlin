package day2

import model.DaySolver

class Day2Solver : DaySolver<List<String>, Int> {
    override fun part1(data: List<String>): Int {
        val result = data.map {
            val x = it.split(' ')
            x[0] to x[1].toInt()
        }
        var point = Pair(0,0)
        result.forEach {
            when(it.first) {
                "forward" -> point = Pair(point.first + it.second, point.second)
                "up" -> point = Pair(point.first, point.second - it.second)
                "down" -> point = Pair(point.first, point.second + it.second)
            }
        }
        return point.first * point.second
    }

    override fun part2(data: List<String>): Int {
        val result = data.map {
            val x = it.split(' ')
            x[0] to x[1].toInt()
        }
        var point = Triple(0,0, 0)
        result.forEach {
            when(it.first) {
                "forward" -> point = Triple(point.first + it.second, point.second + (point.third * it.second), point.third)
                "up" -> point = Triple(point.first, point.second, point.third - it.second)
                "down" -> point = Triple(point.first, point.second, point.third + it.second)
            }
        }
        return point.first * point.second
    }
}