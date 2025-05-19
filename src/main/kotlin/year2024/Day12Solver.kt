package year2024

import model.DaySolver

class Day12Solver : DaySolver<List<String>, Long> {

    override fun part1(data: List<String>): Long {
        var count = 0L
        val d = Array(data.size) {
            LongArray(data[0].length) {
                0
            }
        }
        for (i in data.indices) {
            for (j in data[i].indices) {
                val curr = data[i][j]
                var c = 0L
                if (i - 1 < 0 || data[i - 1][j] != curr) c++
                if (i + 1 >= data.size || data[i + 1][j] != curr) c++
                if (j - 1 < 0 || data[i][j - 1] != curr) c++
                if (j + 1 >= data[0].length || data[i][j + 1] != curr) c++
                d[i][j] = c
            }
        }

        for (i in data.indices) {
            for (j in data[i].indices) {
                if (d[i][j] == -1L) continue
                count +=  perimeter(data, d, i, j, data[i][j]) * area(data, d, i, j, data[i][j])
            }
        }
        return count
    }

    private fun area(data: List<String>, d: Array<LongArray>, i: Int, j: Int, c: Char): Long {
        if (i < 0 || j < 0 || i == data.size || j == data[0].length || d[i][j] == -1L || data[i][j] != c) return 0
        d[i][j] = -1
        val res = 1 + area(data, d, i - 1, j, c) + area(data, d, i + 1, j, c) + area(data, d, i, j - 1, c) + area(
            data,
            d,
            i,
            j + 1,
            c
        )
        return res
    }

    private fun perimeter(data: List<String>, d: Array<LongArray>, i: Int, j: Int, c: Char): Long {
        if (i < 0 || j < 0 || i == data.size || j == data[0].length || d[i][j] == -2L || data[i][j] != c) return 0
        val curr = d[i][j]
        d[i][j] = -2
        val res = curr + perimeter(data, d, i - 1, j, c) + perimeter(data, d, i + 1, j, c) + perimeter(
            data,
            d,
            i,
            j - 1,
            c
        ) + perimeter(data, d, i, j + 1, c)
        return res
    }

    private fun solve(grid: List<List<Int>>, i: Int, j: Int, set: MutableSet<Pair<Int, Int>>): Long {
        if (grid[i][j] == 9) {
            set.add(i to j)
            return 1L
        }
        var count = 0L
        if (i > 0 && grid[i - 1][j] == grid[i][j] + 1) {
            count += solve(grid, i - 1, j, set)
        }
        if (i < grid.size - 1 && grid[i + 1][j] == grid[i][j] + 1) {
            count += solve(grid, i + 1, j, set)
        }
        if (j > 0 && grid[i][j - 1] == grid[i][j] + 1) {
            count += solve(grid, i, j - 1, set)
        }
        if (j < grid[0].size - 1 && grid[i][j + 1] == grid[i][j] + 1) {
            count += solve(grid, i, j + 1, set)
        }
        return count
    }

    override fun part2(data: List<String>): Long {
        var count = 0L
        val d = Array(data.size) {
            LongArray(data[0].length) {
                0
            }
        }
        for (i in data.indices) {
            for (j in data[i].indices) {
                var c = 0L
                if (top(data, i, j)) c++
                if (bottom(data, i, j)) c++
                if (left(data, i, j)) c++
                if (right(data, i, j)) c++
                d[i][j] = c
            }
        }

        for (i in data.indices) {
            for (j in data[i].indices) {
                if (d[i][j] == -1L) continue
                val p = perimeter(data, d, i, j, data[i][j])
                val a = area(data, d, i, j, data[i][j])
                count +=  p * a
            }
        }
        return count
    }

    private fun top(data: List<String>, i: Int, j: Int):Boolean {
        val curr = data[i][j]
        if ((i - 1 < 0 || data[i - 1][j] != curr) && (j - 1 < 0 || data[i][j - 1] != curr)) return true
        else if (i > 0 && j > 0 && data[i-1][j-1] == curr && data[i-1][j] != curr) return true
        return false
    }

    private fun bottom(data: List<String>, i: Int, j: Int):Boolean {
        val curr = data[i][j]
        if ((i + 1 >= data.size || data[i + 1][j] != curr) && (j - 1 < 0 || data[i][j - 1] != curr)) return true
        else if (i+1 < data.size && j > 0 && data[i+1][j-1] == curr && data[i+1][j] != curr) return true
        return false
    }

    private fun left(data: List<String>, i: Int, j: Int):Boolean {
        val curr = data[i][j]
        if ((i - 1 < 0 || data[i - 1][j] != curr) && (j - 1 < 0 || data[i][j - 1] != curr)) return true
        else if (i > 0 && j > 0 && data[i-1][j-1] == curr && data[i][j-1] != curr) return true
        return false
    }

    private fun right(data: List<String>, i: Int, j: Int):Boolean {
        val curr = data[i][j]
        if ((i - 1 < 0 || data[i - 1][j] != curr) && (j + 1 == data[0].length || data[i][j + 1] != curr)) return true
        else if (i > 0 && j +1 < data[0].length && data[i-1][j+1] == curr && data[i][j+1] != curr) return true
        return false
    }

}