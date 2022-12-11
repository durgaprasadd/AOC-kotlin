package day25

import model.DaySolver

class Day35Solver : DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<List<Pair<Int, Int>>> {
        return data.map {
            it.split(",").map {
                val x = it.split("-")
                x[0].toInt() to x[1].toInt()
            }
        }
    }

    override fun part1(data: List<String>): Int {
        var cycles = 0
        var x = 1
        var start = 20
        var strength = 0
        for (line in data) {
            val command = line.split(" ")[0]
            if (command == "noop") {
                cycles++
                if (cycles == start) {
                    strength += (start * x)
                    start += 40
                }
            } else {
                cycles++
                if (cycles == start) {
                    strength += (start * x)
                    start += 40
                }
                cycles++
                if (cycles == start) {
                    strength += (start * x)
                    start += 40
                }
                x += line.split(" ")[1].toInt()
            }
        }
        return strength
    }

    override fun part2(data: List<String>): Int {
        var cycles = 0
        var x = 0
        var result = ""
        for (line in data) {
            val command = line.split(" ")[0]
            if (command == "noop") {

                result += if (cycles in x..x + 2) {
                    "#"
                } else {
                    "."
                }
                cycles++
                cycles %= 40
            } else {

                result += if (cycles in x..x + 2) {
                    "#"
                } else {
                    "."
                }
                cycles++
                cycles %= 40
                result += if (cycles in x..x + 2) {
                    "#"
                } else {
                    "."
                }
                cycles++
                cycles %= 40
                x += line.split(" ")[1].toInt()
            }
        }
        result.chunked(40).map { println(it) }
        return 0
    }

}