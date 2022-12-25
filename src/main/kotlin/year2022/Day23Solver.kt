package year2022

import model.DaySolver
import kotlin.Int.Companion.MAX_VALUE
import kotlin.Int.Companion.MIN_VALUE
import kotlin.math.abs

class Day23Solver : DaySolver<List<String>, Int> {

    private val points = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

    private val directions = listOf(
        listOf(-1 to 0, -1 to -1, -1 to 1),
        listOf(1 to 0, 1 to -1, 1 to 1),
        listOf(0 to -1, -1 to -1, 1 to -1),
        listOf(0 to 1, -1 to 1, 1 to 1)
    )

    private fun parse(data: List<String>): MutableSet<Pair<Int, Int>> {
        val positions = mutableSetOf<Pair<Int, Int>>()
        for (i in data.indices) {
            for (j in data[i].indices) {
                if (data[i][j] == '#') positions.add(i to j)
            }
        }
        return positions
    }

    override fun part1(data: List<String>): Int {
        var positions = parse(data)
        for (i in 0 until 10) {
            positions = round(positions, i)
        }
        var x = MAX_VALUE
        var x1 = MIN_VALUE
        var y = MAX_VALUE
        var y1 = MIN_VALUE
        for ((i, j) in positions) {
            x = minOf(x, i)
            x1 = maxOf(x1, i)
            y = minOf(y, j)
            y1 = maxOf(y1, j)
        }
        x1++
        y1++
        return (abs(x - x1) * abs(y - y1)) - positions.size
    }

    private fun round(positions: Set<Pair<Int, Int>>, i: Int): MutableSet<Pair<Int, Int>> {
        val nextPositions = mutableSetOf<Pair<Int, Int>>()
        val declined = mutableSetOf<Pair<Int, Int>>()
        val map = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
        for (position in positions) {
            if (canMove(position, positions)) {
                val pos = nextMove(position, positions, i)
                if (pos in nextPositions) {
                    declined.add(pos)
                } else {
                    nextPositions.add(pos)
                }
                map[position] = pos
            } else {
                map[position] = position
            }
        }
        val result = mutableSetOf<Pair<Int, Int>>()
        for ((k, v) in map) {
            if (v in declined) {
                result.add(k)
            } else {
                result.add(v)
            }
        }
        return result
    }

    private fun nextMove(position: Pair<Int, Int>, positions: Set<Pair<Int, Int>>, i: Int): Pair<Int, Int> {
        for (j in directions.indices) {
            if (directions[(i + j) % 4].map { it.first + position.first to it.second + position.second }.all {
                    it !in positions
                }) {
                val (x, y) = directions[(i + j) % 4].first()
                return x + position.first to y + position.second
            }
        }
        return position
    }

    private fun canMove(position: Pair<Int, Int>, positions: Set<Pair<Int, Int>>): Boolean {
        return points.map {
            it.first + position.first to it.second + position.second
        }.any {
            it in positions
        }
    }

    override fun part2(data: List<String>): Int {
        var positions = parse(data)
        var i = 0
        while (true) {
            val next = round(positions, i)
            if (positions == next) {
                return i + 1
            }
            positions = next
            i++
        }
    }

}