package year2021.day4

import model.DaySolver

class Day4Solver : DaySolver<List<String>, Int> {
    private fun parseNumbers(data: List<String>): List<Int> {
        return data.first()
            .split(',')
            .filter { x -> x.isNotBlank() }
            .map { x -> x.toInt() }
    }

    private fun parseGrids(data: List<String>): List<List<List<Int>>> {
        return data.drop(1)
            .filter { x -> x.isNotBlank() }
            .chunked(5)
            .map { grid ->
                grid.map { line ->
                    line.split(' ')
                        .filter { x -> x.isNotBlank() }
                        .map { x -> x.toInt() }
                }
            }
    }

    override fun part1(data: List<String>): Int {
        val numbers = parseNumbers(data)
        val grids = parseGrids(data)
        val marked = mutableListOf<Int>()
        for (num in numbers){
            marked.add(num)
            grids.forEach { grid ->
                if (isBingo(grid, marked)) {
                    val sum = grid.sumOf { line -> line.filter { x -> !marked.contains(x) }.sum() }
                    return num*sum
                }
            }
        }
        return -1
    }

    private fun isBingo(grid: List<List<Int>>, nums: List<Int>): Boolean {
        val result = grid.indices.any { i ->
            grid.all { line -> nums.contains(line[i]) }
        }

        return result || grid.any { line -> line.all { x -> nums.contains(x) } }
    }

    override fun part2(data: List<String>): Int {
        val numbers = parseNumbers(data)
        val grids = parseGrids(data)
        val marked = mutableListOf<Int>()
        var filteredGrids = grids
        for (num in numbers) {
            marked.add(num)
            filteredGrids = filteredGrids.filter { grid ->
                val final = isBingo(grid, marked)
                if (final&& filteredGrids.size == 1) {
                    val sum = grid.sumOf { line -> line.filter { x -> !marked.contains(x) }.sum() }
                    return num * sum
                }
                !final
            }
        }
        return -1
    }
}