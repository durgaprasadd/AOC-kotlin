package year2024

import model.DaySolver
import java.util.*
import kotlin.math.abs


class Day20Solver : DaySolver<List<String>, Any> {

    override fun part1(data: List<String>): Long {
        return runWhileCheating(data,2, 100).toLong()
    }

    private fun pos(data: List<String>): Pair<Int,Int> {
        for (i in data.indices) {
            for (j in data[i].indices) {
                if (data[i][j] == 'S') return Pair(i,j)
            }
        }
        return Pair(-1,-1)
    }

    private fun runWhileCheating(data: List<String>, distance: Int, minimalGain: Int): Int {
        var cheatCount = 0
        var step = 0
        var pos: Pair<Int, Int>? = pos(data)
        var prevPos = -1 to -1
        do {
            pos!!
            stepOnPositions[pos] = step
            cheatCount += pos.neighboursADistance(step, distance, minimalGain)

            step++
            val previous = pos
            pos = pos.next(data, prevPos)
            prevPos = previous
        } while (pos != null)
        return cheatCount
    }

    fun Pair<Int, Int>.next(data: List<String>, prevPos: Pair<Int, Int>): Pair<Int, Int>? {
        return when {
            isValidTile(data,this + (1 to 0), prevPos) -> this + (1 to 0)
            isValidTile(data,this + (0 to 1), prevPos) -> this + (0 to 1)
            isValidTile(data,this + (-1 to 0), prevPos) -> this + (-1 to 0)
            isValidTile(data,this + (0 to -1), prevPos) -> this + (0 to -1)
            else -> null
        }
    }

    private fun Pair<Int, Int>.neighboursADistance(step: Int, distance: Int, minimalGain: Int): Int {
        var count = 0
        (-distance..distance).forEach { yOffset ->
            val xDistance = distance - abs(yOffset)
            (-xDistance..xDistance).forEach { xOffset ->
                stepOnPositions[this.first + yOffset to this.second + xOffset]?.let { otherStep ->
                    val savedSteps = step - otherStep - abs(yOffset) - abs(xOffset)
                    if (savedSteps >= minimalGain) count++
                }
            }
        }
        return count
    }

    fun isValidTile(data: List<String>, tile: Pair<Int, Int>, prevPos: Pair<Int, Int>) =
        prevPos != tile && data[tile.first][tile.second] != '#'

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
        return first + other.first to second + other.second
    }
    private val stepOnPositions = mutableMapOf<Pair<Int, Int>, Int>()

    override fun part2(data: List<String>): Long {
        return runWhileCheating(data,20, 100).toLong()
    }

}


