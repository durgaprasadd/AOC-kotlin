package year2024

import model.DaySolver

class Day11Solver : DaySolver<String, Long> {

    private val memo = mutableMapOf<Pair<Long, Int>, Long>()
    override fun part1(data: String): Long {
        val stones = data.split(" ").map { it.toLong() }
        var count = 0L
        for (stone in stones) {
            count += solve(stone, 25)
        }
        return count
    }

    private fun solve(stone: Long, n: Int): Long {
        if (Pair(stone, n) in memo) {
            return memo[Pair(stone, n)]!!
        }
        if (n == 0) {
            return 1L
        }
        if (stone == 0L) {
            val curr = solve(1, n-1)
            memo[Pair(stone, n)] = curr
            return curr
        }

        val s = stone.toString()
        if (s.length%2 == 0) {
            val curr = solve(s.substring(0, s.length/2).toLong(), n-1) + solve(s.substring(s.length/2).toLong(), n-1)
            memo[Pair(stone, n)] = curr
            return curr
        }
        val curr = solve(stone*2024, n-1)
        memo[Pair(stone, n)] = curr
        return curr
    }

    override fun part2(data: String): Long {
        val stones = data.split(" ").map { it.toLong() }
        var count = 0L
        for (stone in stones) {
            count += solve(stone, 75)
        }
        return count
    }

}