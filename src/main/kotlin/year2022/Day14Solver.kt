package year2022

import model.DaySolver

class Day14Solver: DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<List<Pair<Int, Int>>> {
        return data.map {
            it.split(" -> ").map {
                val x = it.split(",")
                x[0].toInt()to x[1].toInt()
            }
        }
    }
    override fun part1(data: List<String>): Int {
        val lines = parse(data)
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        for (line in lines){
            for ((x,y) in line){
                minX = minOf(minX, x)
                maxX = maxOf(maxX, x)
                minY = minOf(minY, y)
                maxY = maxOf(maxY, y)
            }
        }
        val source = Pair(500,0)
        minX = minOf(minX, source.first)
        maxX = maxOf(maxX, source.first)
        minY = minOf(minY, source.second)
        maxY = maxOf(maxY, source.second)
        val grid = Array(maxY-minY + 1){
            IntArray(maxX - minX + 1) {0}
        }
        markPath(grid, lines, minX, minY)
        for (i in 0 until 1000){
            val res = markSand(grid,source.second-minY, source.first-minX)
            if (res){
                return i
            }
        }
        return 0
    }

    private fun markSand(grid: Array<IntArray>, x: Int, y: Int): Boolean {
        var i = x
        var j = y
        if (grid[i][j] != 0){
            return true
        }
        while (i < grid.size && j < grid[0].size){
            if (i+1 == grid.size){
                return true
            }
            if (grid[i+1][j] == 0){
                i++
                continue
            }

            if (j-1 < 0){
                return true
            }
            if (grid[i+1][j-1] == 0){
                j--
                continue
            }

            if (j+1 == grid[0].size){
                return true
            }
            if (grid[i+1][j+1] == 0){
                j++
                continue
            }
            break
        }
        grid[i][j] = 1
        return false
    }

    private fun markPath(grid: Array<IntArray>, lines: List<List<Pair<Int, Int>>>, minX: Int, minY: Int) {
        for (line in lines){
            line.windowed(2).forEach {
                for (x in minOf(it[0].first,it[1].first)..maxOf(it[0].first, it[1].first)){
                    for (y in minOf(it[0].second,it[1].second)..maxOf(it[0].second, it[1].second)){
                        grid[y-minY][x-minX] = 1
                    }
                }
            }
        }
    }

    override fun part2(data: List<String>): Int {
        val lines = parse(data)
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        for (line in lines){
            for ((x,y) in line){
                minX = minOf(minX, x)
                maxX = maxOf(maxX, x)
                minY = minOf(minY, y)
                maxY = maxOf(maxY, y)
            }
        }
        val source = Pair(500,0)
        minX = minOf(minX, source.first)
        maxX = maxOf(maxX, source.first)
        minY = minOf(minY, source.second)
        maxY = maxOf(maxY, source.second)
        val left = Pair(-maxX, maxY+2)
        val right = Pair(maxX+maxX, maxY+2)
        minX = minOf(minX, left.first)
        maxX = maxOf(maxX, right.first)
        maxY = maxOf(maxY, right.second)
        val grid = Array(maxY-minY + 1){
            IntArray(maxX - minX + 1) {0}
        }
        markPath(grid, lines.plusElement(listOf(left,right)), minX, minY)
        for (i in 0 until 100000){
            val res = markSand(grid,source.second-minY, source.first-minX)
            if (res){
                return i
            }
        }
        return 0
    }

}