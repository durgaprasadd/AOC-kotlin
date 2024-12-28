package year2024

import model.DaySolver

class Day6Solver : DaySolver<List<String>, Int> {

    private fun initialPosition(grid: List<CharArray>):Pair<Int,Int> {
        for (i in grid.indices){
            for (j in grid[i].indices){
                if (grid[i][j] == '^'){
                    return i to j
                }
            }
        }
        return -1 to -1
    }
    override fun part1(data: List<String>): Int {
        val grid = data.map {
            it.toCharArray()
        }
        var (r,c) = initialPosition(grid)

        val dr = intArrayOf(-1, 0, 1, 0)
        val dc = intArrayOf(0, 1, 0, -1)
        var dir = 0
        while (true) {
            grid[r][c] = 'O'
            val nextR = r + dr[dir]
            val nextC = c + dc[dir]
            if (nextR !in grid.indices || nextC !in grid[nextR].indices) {
                break
            }
            if (grid[nextR][nextC] == '#') {
                dir = (dir + 1) % 4
            } else {
                r = nextR
                c = nextC
            }
        }
        var count = 0
        for (i in grid){
            for (j in i){
                if (j == 'O') count++
            }
        }
        return count
    }


    override fun part2(data: List<String>): Int {
        val grid = data.map {
            it.toCharArray()
        }
        val (r,c) = initialPosition(grid)


        var count = 0
        for (i in grid.indices){
            for (j in grid[i].indices){
                if (grid[i][j] != '.') continue
                grid[i][j] = '#'
                if (loop(grid,r,c)) count++
                grid[i][j] = '.'
            }
        }
        return count
    }

    private fun loop(grid: List<CharArray>, r1: Int, c1: Int): Boolean {
        val dr = intArrayOf(-1, 0, 1, 0)
        val dc = intArrayOf(0, 1, 0, -1)
        var dir = 0
        var r = r1
        var c = c1
        repeat (grid.size* grid[0].size * 2) {
            val nextR = r + dr[dir]
            val nextC = c + dc[dir]
            if (nextR !in grid.indices || nextC !in grid[nextR].indices) {
                return false
            }
            if (grid[nextR][nextC] == '#') {
                dir = (dir + 1) % 4
            } else {
                r = nextR
                c = nextC
            }
        }
        return true
    }

}