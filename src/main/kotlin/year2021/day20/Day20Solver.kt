package year2021.day20

import model.DaySolver


class Day20Solver : DaySolver<List<String>, Int> {

    companion object {
        const val DARK_CELL = '.'
        const val LIGHT_CELL = '#'
    }

    private fun parseData(data: List<String>): Pair<String, MutableList<String>> {
        var result = ""
        val grid = mutableListOf<String>()
        var algorithm = false
        data.forEach { line ->
            if (line.trim().isNotBlank() && !algorithm) {
                result += line.trim()
            }else if (algorithm) {
                grid.add(line.trim())
            }else {
                algorithm = true
            }
        }
        return Pair(result,grid)
    }

    override fun part1(data: List<String>): Int {
        val (algorithm, grid) = parseData(data)
        return applyImageEnhancementAlgorithm(algorithm, grid,2)
    }

    private fun applyImageEnhancementAlgorithm(algorithm: String, grid: List<String>,times: Int): Int {
        var defaultChar =  DARK_CELL
        var enhancedImage = grid
        for (i in 0 until times) {
            enhancedImage = increaseGrid(enhancedImage, defaultChar)
            val result = mutableListOf<String>()
            for (x in enhancedImage.indices) {
                var updatedLine = ""
                for (y in enhancedImage[x].indices){
                    val line = getLine(x,y,enhancedImage,defaultChar)
                    val binary = convertToBinary(line).toInt(2)
                    updatedLine += algorithm[binary]
                }
                result.add(updatedLine)
            }
            defaultChar = if (defaultChar == DARK_CELL) algorithm[0] else algorithm[511]
            enhancedImage = result
        }
        return enhancedImage.sumOf { line -> line.count { c -> c == LIGHT_CELL } }
    }

    private fun convertToBinary(line: List<Char>): String {
        return line.map { c ->
            if (c == DARK_CELL) '0' else '1'
        }.joinToString("")
    }

    private fun getLine(x: Int, y: Int, grid: List<String>, defaultChar: Char): List<Char> {
        val neighbours = listOf(
            Pair(-1,-1),
            Pair(-1,0),
            Pair(-1,1),
            Pair(0,-1),
            Pair(0,0),
            Pair(0,1),
            Pair(1,-1),
            Pair(1,0),
            Pair(1,1),
        )
        return neighbours.map { (x1,y1) ->
            Pair(x+x1, y+y1)
        }.map { (i,j) ->
            if (isValid(grid,i,j)) grid[i][j] else defaultChar
        }
    }

    private fun isValid(grid: List<String>, i: Int, j: Int): Boolean {
        return i >= 0 && i < grid.size && j >= 0 && j < grid.first().length
    }
    private fun increaseGrid(grid: List<String>, c: Char): List<String> {
        val size = grid.first().length
        val defaultLine = "".padStart(size + 2 , c)
        return listOf(defaultLine) + grid.map { line ->
            line.padStart(size + 1, c).padEnd(size + 2, c)
        } + listOf(defaultLine)
    }

    override fun part2(data: List<String>): Int {
        val (algorithm, grid) = parseData(data)
        return applyImageEnhancementAlgorithm(algorithm, grid,50)
    }
}