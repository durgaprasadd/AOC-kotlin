package year2024

import model.DaySolver

class Day10Solver : DaySolver<List<String>, Long> {

    override fun part1(data: List<String>): Long {
        var count = 0L
        val grid = data.map {
            it.split("").filter { it.isNotBlank() }.map { it.toInt() } }
        for (i in grid.indices){
            for (j in grid[i].indices){
                if (grid[i][j] == 0){
                    val set = mutableSetOf<Pair<Int,Int>>()
                    solve(grid, i, j, set)
                    count += set.size
                }
            }
        }
        return count
    }

    private fun solve(grid: List<List<Int>>, i: Int, j: Int, set: MutableSet<Pair<Int,Int>>) : Long{
        if (grid[i][j] == 9) {
            set.add(i to j)
            return 1L
        }
        var count = 0L
        if (i > 0 && grid[i - 1][j] == grid[i][j]+1) {
            count += solve(grid, i - 1, j, set)
        }
        if (i < grid.size-1 && grid[i + 1][j] == grid[i][j]+1) {
            count += solve(grid, i + 1, j, set)
        }
        if (j > 0 && grid[i][j-1] == grid[i][j]+1) {
            count += solve(grid, i, j-1, set)
        }
        if (j < grid[0].size-1 && grid[i][j+1] == grid[i][j]+1) {
            count += solve(grid, i, j+1, set)
        }
        return count
    }

    override fun part2(data: List<String>): Long {
        var count = 0L
        val grid = data.map {
            it.split("").filter { it.isNotBlank() }.map { it.toInt() } }
        for (i in grid.indices){
            for (j in grid[i].indices){
                if (grid[i][j] == 0){
                    val set = mutableSetOf<Pair<Int,Int>>()
                    count += solve(grid, i, j, set)
                }
            }
        }
        return count
    }

}