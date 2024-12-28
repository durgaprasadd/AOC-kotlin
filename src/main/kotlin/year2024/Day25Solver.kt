package year2024

import model.DaySolver

class Day25Solver : DaySolver<List<String>, Any> {

    override fun part1(data: List<String>): Any {
        val grids = mutableListOf<List<String>>()
        var i = 0
        while (i < data.size) {
            val curr = mutableListOf<String>()
            while (i < data.size && data[i].isNotBlank()) {
                curr.add(data[i])
                i++
            }
            grids.add(curr)
            i++
        }
        val locks = mutableListOf<String>()
        val keys = mutableListOf<String>()
        for (grid in grids) {
            if (isLock(grid)){
                locks.add(parse(grid))
                continue
            }
            if (isKey(grid)) {
                keys.add(parse(grid))
                continue
            }
        }
        return locks.sumOf {l ->
            keys.count {k ->
                k.indices.all {
                    (k[it]-'0') + (l[it] - '0') <= 5 }
            }
        }
    }

    private fun isLock(grid: List<String>): Boolean {
        return grid[0].all { it == '#' } && grid[6].all { it == '.' }
    }

    private fun parse(grid: List<String>): String {
        val res = IntArray(5){-1}
        for (i in grid.indices) {
            for (j in grid[i].indices){
                if (grid[i][j] == '#') {
                    res[j]++
                }
            }
        }
        return res.joinToString("")
    }

    private fun isKey(grid: List<String>): Boolean {
        return grid[6].all { it == '#' } && grid[0].all { it == '.' }
    }

    override fun part2(data: List<String>): Any {
        return 0
    }

}