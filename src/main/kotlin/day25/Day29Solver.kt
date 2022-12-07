package day25

import model.DaySolver

class Day29Solver: DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<List<Pair<Int, Int>>> {
        return data.map {
            it.split(",").map {
                val x = it.split("-")
                x[0].toInt()to x[1].toInt()
            }
        }
    }
    override fun part1(data: List<String>): Int {
        return parse(data).count {(x,y) ->
            (y.first >= x.first && y.second <= x.second) || (x.first >= y.first && x.second <= y.second)
        }
    }

    override fun part2(data: List<String>): Int {
        return parse(data).count {(x,y) ->
            (y.first in x.first..x.second || y.second in x.first.. x.second) || (x.first in y.first..y.second || x.second in y.first.. y.second)
        }
    }

}