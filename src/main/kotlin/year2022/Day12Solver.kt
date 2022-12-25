package year2022

import model.DaySolver
import java.util.PriorityQueue

class Day12Solver: DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<List<Pair<Int, Int>>> {
        return data.map {
            it.split(",").map {
                val x = it.split("-")
                x[0].toInt()to x[1].toInt()
            }
        }
    }
    override fun part1(data: List<String>): Int {
        val rows = data.size
        val cols = data[0].length
        val (x, y) = start(data, rows, cols)
        val (x1, y1) = end(data, rows, cols)
        val grid = data.map { it.toCharArray() }
        grid[x][y] = 'a'
        grid[x1][y1] = 'z'
        return solve(grid, rows, cols, x, y, x1, y1)
    }

    private fun solve(data: List<CharArray>, rows: Int, cols: Int, x:Int, y:Int, x1:Int, y1:Int):Int {
        val q = PriorityQueue<Pair<Int, Pair<Int,Int>>>(kotlin.Comparator { o1, o2 ->
            o1.first - o2.first
        })
        q.add(0 to Pair(x,y))
        val visited = mutableSetOf<Pair<Int,Int>>()
        while (q.isNotEmpty()){
            val (res, pos) = q.poll()
            val (x3,y3) = pos
            if (pos in visited) continue
            visited.add(pos)
            listOf(
                x3+1 to y3,
                x3-1 to y3,
                x3 to y3+1,
                x3 to y3-1
            ).filter {(x2, y2) ->
                x2 >= 0 && y2 >= 0 && x2 < rows && y2 < cols && data[x2][y2] - data[x3][y3] <= 1 && Pair(x2,y2) !in visited
            }.forEach {(x2, y2) ->
                if (x2 == x1 && y2 == y1){

                    return res+1
                }
                q.add((res+1) to Pair(x2,y2))
            }

        }
        return -1;
    }

    private fun start(data: List<String>, rows: Int, cols: Int): Pair<Int, Int> {
        for (i in 0 until rows){
            for (j in 0 until cols){
                if (data[i][j] == 'S'){
                    return i to j
                }
            }
        }
        return -1 to -1
    }

    private fun end(data: List<String>, rows: Int, cols: Int): Pair<Int, Int> {
        for (i in 0 until rows){
            for (j in 0 until cols){
                if (data[i][j] == 'E'){
                    return i to j
                }
            }
        }
        return -1 to -1
    }

    private fun solve(data: List<CharArray>, rows: Int, cols: Int, x:Int, y:Int):Int {
        val q = PriorityQueue<Pair<Int, Pair<Int,Int>>>(kotlin.Comparator { o1, o2 ->
            o1.first - o2.first
        })
        q.add(0 to Pair(x,y))
        val visited = mutableSetOf<Pair<Int,Int>>()
        while (q.isNotEmpty()){
            val (res, pos) = q.poll()
            val (x3,y3) = pos
            if (pos in visited) continue
            visited.add(pos)
            listOf(
                x3+1 to y3,
                x3-1 to y3,
                x3 to y3+1,
                x3 to y3-1
            ).filter {(x2, y2) ->
                x2 >= 0 && y2 >= 0 && x2 < rows && y2 < cols && data[x3][y3] - data[x2][y2] <= 1 && Pair(x2,y2) !in visited
            }.forEach {(x2, y2) ->
                if (data[x2][y2] == 'a'){
                    return res+1
                }
                q.add((res+1) to Pair(x2,y2))
            }

        }
        return -1;
    }

    override fun part2(data: List<String>): Int {
        val rows = data.size
        val cols = data[0].length
        val (x, y) = start(data, rows, cols)
        val (x1, y1) = end(data, rows, cols)
        val grid = data.map { it.toCharArray() }
        grid[x][y] = 'a'
        grid[x1][y1] = 'z'
        return solve(grid, rows, cols, x1, y1)
    }

}