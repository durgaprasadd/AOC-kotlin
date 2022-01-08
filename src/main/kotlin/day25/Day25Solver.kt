package day25

import day24.Block
import model.DaySolver

class Day25Solver : DaySolver<List<String>, Int> {

    private fun parseData(data: List<String>): Block {
        return data.map {
            it.split("").map { it.trim() }.filter { it.isNotBlank() }
        }
    }

    override fun part1(data: List<String>): Int {
        val parsedData = parseData(data)
        return move(parsedData)
    }

    private fun move(data: Block): Int {
        var isDone = false
        var prev = data
        val vertical = data.size
        val horizontal = data.first().size
        var count = 0
        while (!isDone) {
            isDone = true
            val next = prev.map { it.toMutableList() }
            for (i in 0 until vertical){
                for (j in 0 until horizontal){
                    if (prev[i][j] == ">"){
                        val nextPos = (j + 1) % horizontal
                        if (prev[i][nextPos] == "."){
                            next[i][nextPos] = ">"
                            next[i][j] = "."
                            isDone = false
                        }
                    }
                }
            }
            prev = next.map { it.toList() }
            for (i in 0 until vertical){
                for (j in 0 until horizontal){
                    if (prev[i][j] == "v"){
                        val nextPos = (i + 1) % vertical
                        if (prev[nextPos][j] == "."){
                            next[nextPos][j] = "v"
                            next[i][j] = "."
                            isDone = false
                        }
                    }
                }
            }
            prev = next.map { it.toList() }
            count++
        }
        return count
    }

    override fun part2(data: List<String>): Int {
        return 0
    }
}