package year2021.day11

import model.DaySolver

class Day11Solver : DaySolver<List<String>, Long> {

    private fun parseData(data: List<String>): List<MutableList<Int>> {
        return data.map {
            it.split("").map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }.toMutableList()
        }
    }

    override fun part1(data: List<String>): Long {
        return count(parseData(data))
    }

    private fun allPoints(data: List<List<Int>>, point: Pair<Int,Int>): List<Pair<Int,Int>> {
        val points = listOf(
            Pair(-1,0),
            Pair(-1,1),
            Pair(-1,-1),
            Pair(0,-1),
            Pair(0,1),
            Pair(1,0),
            Pair(1,1),
            Pair(1,-1)
        )
        return  points.map { (x, y) -> Pair(point.first + x, point.second + y) }.filter { (x, y) ->
            x >= 0 && x < data.size && y >= 0 && y < data.first().size
        }
    }

    private fun count(data: List<MutableList<Int>>): Long {
        var result = 0L
        for (x in 1..100) {
            val flashed = mutableListOf<Pair<Int,Int>>()
            for (i in data.indices) {
                for (j in data[i].indices) {
                    val value = data[i][j]
                    if (value == 0 && flashed.contains(Pair(i,j))) {
                        continue
                    }
                    if (value == 9) {
                        data[i][j] = 0
                        flashed.add(Pair(i,j))
                        flash(data, Pair(i,j),flashed)
                        continue
                    }
                    data[i][j] = value + 1
                }
            }
            result += data.sumOf { line -> line.count { y -> y == 0 } }
        }
        return result
    }

    private fun flash(data: List<MutableList<Int>>, point: Pair<Int, Int>, flashed: MutableList<Pair<Int,Int>>) {
        val points = allPoints(data, point)
        points.forEach { (x, y) ->
            val value = data[x][y]
            if (value == 9){
                data[x][y] = 0
                flashed.add(Pair(x,y))
                flash(data, Pair(x,y), flashed)
            }else if (!flashed.contains(Pair(x,y))) {
                data[x][y] = value + 1
            }
        }
    }

    private fun count1(data: List<MutableList<Int>>): Long {
        var result = 0L
        var done = false
        while (!done) {
            val flashed = mutableListOf<Pair<Int,Int>>()
            for (i in data.indices) {
                for (j in data[i].indices) {
                    val value = data[i][j]
                    if (value == 0 && flashed.contains(Pair(i,j))) {
                        continue
                    }
                    if (value == 9) {
                        data[i][j] = 0
                        flashed.add(Pair(i,j))
                        flash(data, Pair(i,j),flashed)
                        continue
                    }
                    data[i][j] = value + 1
                }
            }
            done = data.all { line -> line.all { z -> z == 0 } }
            result++
        }
        return result
    }


    override fun part2(data: List<String>): Long {
        return count1(parseData(data))
    }
}