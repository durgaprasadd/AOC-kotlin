package year2024

import model.DaySolver

class Day1Solver : DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): Pair<List<Int>, List<Int>> {
        val res1 = mutableListOf<Int>()
        val res2 = mutableListOf<Int>()
        for (l in data) {
            val (x, y) = l.split("   ").map { it.toInt() }
            res1.add(x)
            res2.add(y)
        }
        return res1 to res2
    }

    override fun part1(data: List<String>): Int {
        val (l1, l2) = parse(data)
        val curr1 = l1.sorted()
        val curr2 = l2.sorted()
        var res = 0
        for (i in curr1.indices) {
            res += abs(curr1[i] - curr2[i])
        }
        return res
    }

    private fun abs(i: Int): Int {
        return if (i < 0) -i else i
    }

    override fun part2(data: List<String>): Int {
        val (l1, l2) = parse(data)
        val f = mutableMapOf<Int, Int>()
        for (x in l2) {
            f[x] = f.getOrDefault(x, 0) + 1
        }
        var res = 0
        for (x in l1) {
            res += x * f.getOrDefault(x, 0)
        }
        return res
    }

}