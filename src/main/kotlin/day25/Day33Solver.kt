package day25

import model.DaySolver

class Day33Solver : DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<List<Int>> {
        return data.map {
            it.map {
                it.toString().toInt()
            }
        }
    }

    override fun part1(data: List<String>): Int {
        val grid = parse(data)
        val rows = grid.size
        val cols = grid[0].size
        var count = 0
        for (i in 1 until rows - 1) {
            for (j in 1 until cols - 1) {
                if (top(grid, i, j, rows, cols) || bottom(grid, i, j, rows, cols) || left(
                        grid,
                        i,
                        j,
                        rows,
                        cols
                    ) || right(grid, i, j, rows, cols)
                ) {
                    count++
                }
            }
        }
        return count + rows + rows + cols - 2 + cols - 2
    }

    fun top(grid: List<List<Int>>, i: Int, j: Int, rows: Int, cols: Int): Boolean {
        val curr = grid[i][j]
        for (x in 0 until i) {
            if (grid[x][j] >= curr) {
                return false
            }
        }
        return true
    }

    fun top1(grid: List<List<Int>>, i: Int, j: Int, rows: Int, cols: Int): Int {
        val curr = grid[i][j]
        var result = 0
        for (x in i - 1 downTo 0) {
            result++
            if (grid[x][j] >= curr) {
                break
            }
        }
        return result
    }

    fun bottom(grid: List<List<Int>>, i: Int, j: Int, rows: Int, cols: Int): Boolean {
        val curr = grid[i][j]
        for (x in i + 1 until rows) {
            if (grid[x][j] >= curr) {
                return false
            }
        }
        return true
    }

    fun bottom1(grid: List<List<Int>>, i: Int, j: Int, rows: Int, cols: Int): Int {
        val curr = grid[i][j]
        var result = 0
        for (x in i + 1 until rows) {
            result++
            if (grid[x][j] >= curr) {
                break
            }
        }
        return result
    }

    fun right(grid: List<List<Int>>, i: Int, j: Int, rows: Int, cols: Int): Boolean {
        val curr = grid[i][j]
        for (x in j + 1 until cols) {
            if (grid[i][x] >= curr) {
                return false
            }
        }
        return true
    }

    fun right1(grid: List<List<Int>>, i: Int, j: Int, rows: Int, cols: Int): Int {
        val curr = grid[i][j]
        var result = 0
        for (x in j + 1 until cols) {
            result++
            if (grid[i][x] >= curr) {
                break
            }
        }
        return result
    }

    fun left(grid: List<List<Int>>, i: Int, j: Int, rows: Int, cols: Int): Boolean {
        val curr = grid[i][j]
        for (x in 0 until j) {
            if (grid[i][x] >= curr) {
                return false
            }
        }
        return true
    }

    fun left1(grid: List<List<Int>>, i: Int, j: Int, rows: Int, cols: Int): Int {
        val curr = grid[i][j]
        var result = 0
        for (x in j - 1 downTo 0) {
            result++
            if (grid[i][x] >= curr) {
                break
            }
        }
        return result
    }

    override fun part2(data: List<String>): Int {
        val grid = parse(data)
        val rows = grid.size
        val cols = grid[0].size
        var max = 0
        for (i in 0 until rows ) {
            for (j in 0 until cols ) {
                val curr = top1(grid, i, j, rows, cols) * bottom1(grid, i, j, rows, cols) * left1(
                    grid,
                    i,
                    j,
                    rows,
                    cols
                ) * right1(grid, i, j, rows, cols)
                max = maxOf(max, curr)
            }
        }
        return max
    }

}