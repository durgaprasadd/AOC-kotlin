package day15

import model.DaySolver
import java.util.*

class Day15Solver : DaySolver<List<String>, Int> {

    private fun parseData(data: List<String>): List<List<Int>> {
        return data.map { it.split("").map { x-> x.trim() }.filter { x -> x.isNotBlank() }.map { x -> x.toInt() } }
    }

    override fun part1(data: List<String>): Int {
        return findLowestPath(parseData(data))
    }

    private fun findLowestPath(parseData: List<List<Int>>): Int {
        val updated = parseData.map { it.map { 100000 }.toMutableList() }
        updated[0][0] = parseData[0][0]
        parseData.first().forEachIndexed {index, v ->
            if (index == 0) {
                updated[0][index] = v
            } else {
                updated[0][index] = updated[0][index - 1] + v
            }
        } // horizontal

        parseData.forEachIndexed { index, list ->
            if (index == 0) {
                updated[index][0] = list[0]
            } else {
                updated[index][0] = updated[index - 1][0] + list[0]
            }
        } // vertical

        for (i in 1 until parseData.size) {
            for( j in 1 until parseData.first().size) {
                updated[i][j] = minOf(updated[i][j-1], updated[i-1][j]) + parseData[i][j]
            }
        }
        for (i in updated.indices){
            for (j in updated.first().indices){
                checkAllPaths(parseData, updated, Pair(i,j))
            }
        }
       return updated.last().last()
    }

    private fun checkAllPaths(parseData: List<List<Int>>, updated:List<MutableList<Int>>, point: Pair<Int, Int>) {
        val (x1,y1) = point
        if (x1 == updated.size-1 && y1 == updated.first().size -1) {
            return
        }
        listOf(
            Pair(0,1),
            Pair(0,-1),
            Pair(1,0),
            Pair(-1,0),
        ).map {(x,y) ->
            Pair(x+x1, y+y1)
        }.filter { (x,y) ->
            x >=0 && y >= 0 && x < updated.size && y < updated.first().size && !(x == 0 && y == 0)
        }.forEach { (x,y) ->
            if (updated[x1][y1] + parseData[x][y] < updated[x][y]){
                updated[x][y] = updated[x1][y1] + parseData[x][y]
                checkAllPaths(parseData, updated, Pair(x,y))
            }
        }
    }

    private fun findLowestPathUsingAlgo(data: List<List<Int>>): Int {
        val visited = mutableSetOf<Pair<Int,Int>>()
        val priorityQueue = PriorityQueue<Triple<Int,Int,Int>>(compareBy { it.first })
        priorityQueue.add(Triple(0,0,0))
        while (priorityQueue.isNotEmpty()) {
            val point = priorityQueue.poll()
            val (risk, x1,y1) = point
            visited.add(Pair(x1,y1))
            listOf(
                Pair(0,1),
                Pair(0,-1),
                Pair(1,0),
                Pair(-1,0),
            ).map {(x,y) ->
                Pair(x+x1, y+y1)
            }.filter { (x,y) ->
                x >=0 && y >= 0 && x < data.size && y < data.first().size && !visited.contains(Pair(x,y))
            }.forEach { pair ->
                val (x,y) = pair
                if (x == data.size - 1&& y == data.first().size - 1){
                    return risk + data[x][y]
                }
                priorityQueue.add(Triple(risk + data[x][y], x,y))
            }
        }
        return 0
    }

    override fun part2(data: List<String>): Int {
        val grid = createGrid(parseData(data))
        return findLowestPath(grid)
    }

    private fun createGrid(parseData: List<List<Int>>): List<List<Int>> {
        val updated = parseData.map { line ->
            (0 until 5).flatMap { i ->
                line.map { x ->
                     if (x + i > 9 ) (x + i - 9) else x + i
                }
            }
        }
        return (0 until 5).flatMap { i -> updated.map { line -> line.map { x ->
            if (x + i > 9 ) (x + i - 9) else x + i
        } } }
    }
}