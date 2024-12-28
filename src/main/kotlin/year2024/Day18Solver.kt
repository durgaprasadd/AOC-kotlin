package year2024

import model.DaySolver
import java.util.PriorityQueue

class Day18Solver : DaySolver<List<String>, Any> {

    override fun part1(data: List<String>): Long {
        val size = 71
        val grid = List(size){
            MutableList(size) {'.'}
        }
        data.take(1024).map {
            it.trim().split(",").map { it.toInt() }
        }.forEach {(x,y) ->
            grid[y][x] = '#'
        }
        grid.forEach { println(it) }
        val q = PriorityQueue<List<Int>> (
            compareBy{it[2]}
        )
        q.add(listOf(0,0,0))
        val vis = mutableSetOf<Pair<Int,Int>>()
        while (q.isNotEmpty()) {
            val (i, j, d) = q.poll()
            if(i == grid.size-1 && j == grid.size-1) {
                return d.toLong()
            }
            if (vis.contains(i to j)) continue
            vis.add(i to j)
            if (i+1 < grid.size && grid[i+1][j] == '.') {
                q.add(listOf(i+1, j, d+1))
            }
            if (j+1 < grid.size && grid[i][j+1] == '.') {
                q.add(listOf(i, j+1, d+1))
            }
            if (i-1 >= 0 && grid[i-1][j] == '.') {
                q.add(listOf(i-1, j, d+1))
            }
            if (j-1 >= 0 && grid[i][j-1] == '.') {
                q.add(listOf(i, j-1, d+1))
            }
        }
        var count = 0L
        return count
    }


    override fun part2(data: List<String>): Pair<Int,Int> {
        val size = 71
        val grid = List(size){
            MutableList(size) {'.'}
        }
        data.take(1024).map {
            it.trim().split(",").map { it.toInt() }
        }.forEach {(x,y) ->
            grid[y][x] = '#'
        }
        grid.forEach { println(it) }
        for (i in 1024 until data.size) {
            val (x,y) = data[i].trim().split(",").map { it.toInt() }
            grid[y][x] = '#'
            val curr = solve(grid)
            if (curr == -1) {
                return x to y
            }
        }
        return -1 to -1
    }

    private fun solve(grid: List<MutableList<Char>>):Int{
        val q = PriorityQueue<List<Int>> (
            compareBy{it[2]}
        )
        q.add(listOf(0,0,0))
        val vis = mutableSetOf<Pair<Int,Int>>()
        while (q.isNotEmpty()) {
            val (i, j, d) = q.poll()
            if(i == grid.size-1 && j == grid.size-1) {
                return d
            }
            if (vis.contains(i to j)) continue
            vis.add(i to j)
            if (i+1 < grid.size && grid[i+1][j] == '.') {
                q.add(listOf(i+1, j, d+1))
            }
            if (j+1 < grid.size && grid[i][j+1] == '.') {
                q.add(listOf(i, j+1, d+1))
            }
            if (i-1 >= 0 && grid[i-1][j] == '.') {
                q.add(listOf(i-1, j, d+1))
            }
            if (j-1 >= 0 && grid[i][j-1] == '.') {
                q.add(listOf(i, j-1, d+1))
            }
        }
        return -1
    }

}