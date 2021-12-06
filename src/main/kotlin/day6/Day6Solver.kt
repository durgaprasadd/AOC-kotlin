package day6

import model.DaySolver

class Day6Solver : DaySolver<String, Long> {

    override fun part1(data: String): Long {
        return findTotalFishes(data, 80)
    }

    override fun part2(data: String): Long {
        return findTotalFishes(data, 256)
    }

    private fun findTotalFishes(data: String, days: Int): Long {
        val lanternFishes = (0..8).map { 0L }.toMutableList()
        data.split(",").map { it.toInt() }.forEach {
            lanternFishes[it] += 1L
        }
        for (i in 0 until days) {
            val newBorn = lanternFishes.removeAt(0)
            lanternFishes.add(newBorn)
            lanternFishes[6] += newBorn
        }
        return lanternFishes.sum()
    }

}