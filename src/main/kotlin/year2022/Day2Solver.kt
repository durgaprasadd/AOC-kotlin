package year2022

import model.DaySolver

class Day2Solver: DaySolver<List<String>, Int> {

    private val opp = mapOf(
        "A X" to 4,
        "A Y" to 8,
        "A Z" to 3,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 7,
        "C Y" to 2,
        "C Z" to 6
    )

    private val opp1 = mapOf(
        "A X" to 3,
        "A Y" to 4,
        "A Z" to 8,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 2,
        "C Y" to 6,
        "C Z" to 7
    )

    override fun part1(data: List<String>): Int {
        return data.sumOf {
            opp[it]!!
        }
    }

    override fun part2(data: List<String>): Int {
        return data.sumOf {
            opp1[it]!!
        }
    }

}