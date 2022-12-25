package year2022

import model.DaySolver
import java.util.*

typealias Coordinate = Triple<Int, Int, Int>

class Day24Solver : DaySolver<List<String>, Int> {
    private val directions = listOf(
        0 to 1,
        0 to -1,
        -1 to 0,
        1 to 0,
        0 to 0
    )


    override fun part1(data: List<String>): Int {
        return solve(data)
    }

    private fun solve(data: List<String>): Int {
        val wall = mutableSetOf<Pair<Int, Int>>()
        val rightBlizz = mutableSetOf<Pair<Int, Int>>()
        val leftBlizz = mutableSetOf<Pair<Int, Int>>()
        val upBlizz = mutableSetOf<Pair<Int, Int>>()
        val downBlizz = mutableSetOf<Pair<Int, Int>>()
        var startPoint = 0 to 0
        var endPoint = 0 to 0
        for (y in data.indices) {
            for (x in data[y].indices) {
                val pos = x to y
                when (data[y][x]) {
                    '.' -> {
                        if (y == 0) {
                            startPoint = pos
                        } else {
                            endPoint = pos
                        }
                    }
                    '>' -> {
                        rightBlizz.add(pos)
                    }
                    '<' -> {
                        leftBlizz.add(pos)
                    }
                    '^' -> {
                        upBlizz.add(pos)
                    }
                    'v' -> downBlizz.add(pos)
                    '#' -> wall.add(pos)
                }
            }
        }

        wall.add(startPoint.first to startPoint.second - 1)
        wall.add(endPoint.first to endPoint.second + 1)
        val width = data[0].length - 2
        val height = data.size - 2
        val lcm = LCM(width, height)
        println("$width $height $lcm $startPoint $endPoint")
        val queue: Queue<Pair<Int, Pair<Int, Int>>> = LinkedList()
        val currBizz = mutableSetOf<Pair<Int, Int>>()
        val core = mutableSetOf<Pair<Int, Pair<Int, Int>>>()
        var lastMin = 0
        queue.add(0 to startPoint)
        while (queue.isNotEmpty()) {
            val (currMin, loc) = queue.remove()
            println(currMin to loc)
            val newMin = currMin + 1
            if (loc == endPoint) {
                return currMin
            }
            if (newMin > lastMin) {
                lastMin = newMin
                currBizz.clear()
                for ((X, Y) in upBlizz) {
                    val NY = ((Y - newMin - 1 + height) % height) + 1
                    currBizz.add(X to NY)
                }
                for ((X, Y) in downBlizz) {
                    val NY = ((Y + newMin - 1 + height) % height) + 1
                    currBizz.add(X to NY)
                }
                for ((X, Y) in rightBlizz) {
                    val NX = ((X + newMin - 1 + width) % width) + 1
                    currBizz.add(NX to Y)
                }
                for ((X, Y) in leftBlizz) {
                    val NX = ((X - newMin - 1 + width) % width) + 1
                    currBizz.add(NX to Y)
                }
            }
            val (x, y) = loc
            for ((dx, dy) in directions) {
                val NX = x + dx
                val NY = y + dy
                val newLoc = NX to NY
                val minModulo = newMin%lcm
                val newCheck = minModulo to newLoc
                if (newLoc !in wall && newLoc !in currBizz && newCheck !in core){
                    queue.add(newMin to newLoc)
                    core.add(newCheck)
                }
            }
        }
        return 0
    }

    private fun LCM(width: Int, height: Int): Int {
        return (width * height) / gcd(width, height)
    }

    private fun gcd(a: Int, b: Int): Int {
        if (a < b) return gcd(b, a)
        return if (a % b == 0) b else gcd(b, a % b)
    }

    override fun part2(data: List<String>): Int {
        return 0
    }

}

fun Coordinate.toInt(): Int {
    return (first * 10000) + (second * 1000) + (third * 100)
}