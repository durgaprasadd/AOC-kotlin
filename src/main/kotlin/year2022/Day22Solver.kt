package year2022

import model.DaySolver

class Day22Solver : DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): Pair<List<String>, String> {
        var i = 0
        val result = mutableListOf<String>()
        var max = 0
        while (data[i].isNotBlank()) {
            result.add(data[i])
            max = maxOf(data[i].length, max)
            i++
        }

        return result.map {
            it.padEnd(max, ' ')
        } to data[i + 1]
    }

    override fun part1(data: List<String>): Int {
        val (grid, commands) = parse(data)
        val rows = grid.size
        val cols = grid[0].length
        var row = 0
        var col = grid[0].indexOf('.')
        val directions = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
        var direction = 0
        var i = 0
        while (i < commands.length) {
            if (commands[i].isDigit()) {
                var moves = "" + commands[i]
                i++
                while (i < commands.length && commands[i].isDigit()) {
                    moves += commands[i]
                    i++
                }
                val (x, y) = directions[direction]
                var n = moves.toInt()
                while (n > 0) {
                    var nextRow = (row + x + rows) % rows
                    var nextCol = (col + y + cols) % cols
                    while (grid[nextRow][nextCol] == ' '){
                        nextRow = (nextRow + x + rows) % rows
                        nextCol = (nextCol + y + cols) % cols
                    }
                    if (grid[nextRow][nextCol] == '#'){
                        break
                    }else {
                        row = nextRow
                        col = nextCol
                        n--
                    }
                }
                continue
            }
            direction = if (commands[i] == 'R') {
                (direction + 1) % directions.size
            } else {
                (3 + direction) % 4
            }
            i++
        }
        return ((row+1) * 1000) + ((col+1)*4) + direction
    }

    override fun part2(data: List<String>): Int {
        return 0
    }

}