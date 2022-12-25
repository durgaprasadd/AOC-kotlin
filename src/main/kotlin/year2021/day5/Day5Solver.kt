package year2021.day5

import model.DaySolver

class Day5Solver : DaySolver<List<String>, Int> {
    private fun parseData(data: List<String>): List<List<Int>> {
        return data.map { line ->
            line.split(" -> ").flatMap { it.split(",").map { it.toInt() } }
        }
    }

    private fun initialiseGrid(data: List<List<Int>>): List<MutableList<Int>> {
        val maxX = data.maxOf { line -> maxOf(line[0],line[2]) }
        val maxY = data.maxOf { line -> maxOf(line[1],line[3]) }
        return (0..maxX).map { (0..maxY).map { 0 }.toMutableList() }
    }

    override fun part1(data: List<String>): Int {
        val parsedData = parseData(data)
        val grid = initialiseGrid(parsedData)
        parsedData.forEach { line ->
            val (x1,y1,x2,y2) = line
            if (x1 == x2) {
                val range = if (y1 < y2) y1..y2 else y2..y1
                range.forEach { y ->
                    grid[x1][y] += 1
                }
            }
            if (y1 == y2) {
                val range = if (x1 < x2) x1..x2 else x2..x1
                range.forEach { x ->
                    grid[x][y1] += 1
                }
            }
        }
        return grid.sumOf { line -> line.count { x -> x > 1 } }
    }

    override fun part2(data: List<String>): Int {
        val parsedData = parseData(data)
        val grid = initialiseGrid(parsedData)
        parsedData.forEach { line ->
            var (x1,y1,x2,y2) = line
            val incX = if (x1 == x2) 0 else if (x1 < x2) 1 else -1
            val incY = if (y1 == y2) 0 else if (y1 < y2) 1 else -1
            while (x1 != x2 || y1 != y2) {
                grid[x1][y1] += 1
                x1 += incX
                y1 += incY
            }
            grid[x2][y2] += 1
        }
        return grid.sumOf { line -> line.count { x -> x > 1 } }
    }
}