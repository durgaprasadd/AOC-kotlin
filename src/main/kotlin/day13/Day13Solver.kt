package day13

import model.DaySolver

class Day13Solver : DaySolver<List<String>, Int> {

    private fun parseData(data: List<String>): Pair<List<Pair<Int, Int>>, List<Pair<String, Int>>> {
        val index = data.indexOf("")
        val points = data.subList(0, index).map { it.split(",") }.map { it[0].toInt() to it[1].toInt() }
        val folds = data.subList(index+1, data.size).map { it.split("fold along ")[1] }.map { it.split("=") }.map { it[0].trim() to it[1].toInt() }
         return points to folds
    }

    override fun part1(data: List<String>): Int {
        val (points, folds) = parseData(data)
        return fold(points, folds).size
    }

    private fun fold(points: List<Pair<Int, Int>>, folds: List<Pair<String, Int>>): List<Pair<Int, Int>> {
        return when(folds.first().first){
            "x" -> verticalFold(points, folds.first().second)
            else -> horizontalFold(points, folds.first().second)
        }
    }

    private fun allFolds(points: List<Pair<Int, Int>>, folds: List<Pair<String, Int>>): List<Pair<Int, Int>> {
        var result = points
        folds.forEach { (direction, axis) ->
            result = when(direction){
                "x" -> verticalFold(result, axis)
                else -> horizontalFold(result, axis)
            }
        }
        return result
    }

    private fun horizontalFold(points: List<Pair<Int, Int>>, axis: Int): List<Pair<Int, Int>> {
       return points.filter {(_,y) -> y != axis
        }.map { (x, y) ->
            when {
                y < axis -> Pair(x,y)
                else -> Pair(x, axis - (y - axis))
            }
        }.fold(emptyList()){ acc, pair ->
            if (acc.contains(pair)) acc else acc.plus(pair)
        }
    }

    private fun verticalFold(points: List<Pair<Int, Int>>, axis: Int): List<Pair<Int, Int>> {
        return points.filter {(x, _) -> x != axis
        }.map { (x, y) ->
            when {
                x < axis -> Pair(x,y)
                else -> Pair(axis - (x - axis), y)
            }
        }.fold(emptyList()){ acc, pair ->
            if (acc.contains(pair)) acc else acc.plus(pair)
        }
    }

    override fun part2(data: List<String>): Int {
        val (points, folds) = parseData(data)
        val result = allFolds(points, folds)
        printGrid(result)
        return 0
    }

    private fun printGrid(points: List<Pair<Int, Int>>) {
        val maxX = points.maxOf { it.first }
        val maxY = points.maxOf { it.second }
        val grid = (0..maxY).map { (0..maxX).map { "." }.toMutableList() }
        points.forEach {(x,y) -> grid[y][x] = "#" }
        grid.forEach { println(it.joinToString("")) }
    }
}