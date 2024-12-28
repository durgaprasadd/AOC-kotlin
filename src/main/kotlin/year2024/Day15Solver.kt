package year2024

import model.DaySolver
import java.io.FileWriter

class Day15Solver : DaySolver<List<String>, Long> {

    private fun pos(grid: MutableList<CharArray>): Pair<Int, Int> {
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == '@') return i to j
            }
        }
        return -1 to -1
    }

    private fun pos1(grid: MutableList<MutableList<Char>>): Pair<Int, Int> {
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == '@') return i to j
            }
        }
        return -1 to -1
    }
    override fun part1(data: List<String>): Long {
        var i = 0
        val grid = mutableListOf<CharArray>()
        while (i < data.size){
            if (data[i].isBlank()) break
            grid.add(
                data[i].toCharArray()
            )
            i++
        }
        val moves = StringBuilder()
        while (i < data.size){
            moves.append(data[i])
            i++
        }
        var (x, y) = pos(grid)
        for (m in moves) {
            when (m) {
                '<' -> {
                    left(grid, x, y-1)
                    if (grid[x][y-1] == '.') {
                        grid[x][y-1] = grid[x][y]
                        grid[x][y] = '.'
                        y--
                    }
                }
                '^' -> {
                    top(grid, x-1, y)
                    if (grid[x - 1][y] == '.') {
                        grid[x - 1][y] = grid[x][y]
                        grid[x][y] = '.'
                        x--
                    }
                }
                '>' -> {
                    right(grid, x, y+1)
                    if (grid[x][y+1] == '.') {
                        grid[x][y+1] = grid[x][y]
                        grid[x][y] = '.'
                        y++
                    }
                }
                'v' -> {
                    down(grid, x+1, y)
                    if (grid[x + 1][y] == '.') {
                        grid[x + 1][y] = grid[x][y]
                        grid[x][y] = '.'
                        x++
                    }
                }
            }
        }
        var count = 0L
        for (m in grid.indices) {
            for (n in grid[m].indices) {
                if (grid[m][n] == 'O') {
                    count += (m * 100) + n
                }
            }
        }

        return count
    }

    private fun top(grid: MutableList<CharArray>, i: Int, j: Int) {
        if (grid[i][j] == '#' || grid[i][j] == '.') return
        top(grid, i-1, j)
        if (grid[i-1][j] == '.') {
            grid[i-1][j] = grid[i][j]
            grid[i][j] = '.'
        }
    }

    private fun top(grid: MutableList<MutableList<Char>>, i: Int, j:Set<Int>) {
        if (j.any { grid[i][it] == '#'}) return
        if(j.all { grid[i][it] == '.'}) return
        val next = mutableSetOf<Int>()
        for (k in j) {
            if (grid[i][k] == '[') {
                next.add(k)
                next.add(k+1)
            }
            if (grid[i][k] == ']') {
                next.add(k)
                next.add(k-1)
            }
        }
        top(grid, i-1, next)
        if(next.all { grid[i-1][it] == '.'}) {
            for (k in next) {
                grid[i-1][k] = grid[i][k]
                grid[i][k] = '.'
            }
        }
    }

    private fun down(grid: MutableList<MutableList<Char>>, i: Int, j:Set<Int>) {
        if (j.any { grid[i][it] == '#'}) return
        if(j.all { grid[i][it] == '.'}) return
        val next = mutableSetOf<Int>()
        for (k in j) {
            if (grid[i][k] == '[') {
                next.add(k)
                next.add(k+1)
            }
            if (grid[i][k] == ']') {
                next.add(k)
                next.add(k-1)
            }
        }
        down(grid, i+1, next)
        if(next.all { grid[i+1][it] == '.'}) {
            for (k in next) {
                grid[i+1][k] = grid[i][k]
                grid[i][k] = '.'
            }
        }
    }

    private fun down(grid: MutableList<CharArray>, i: Int, j: Int) {
        if (grid[i][j] == '#' || grid[i][j] == '.') return
        down(grid, i+1, j)
        if (grid[i+1][j] == '.') {
            grid[i+1][j] = grid[i][j]
            grid[i][j] = '.'
        }
    }

    private fun left(grid: MutableList<CharArray>, i: Int, j: Int) {
        if (grid[i][j] == '#' || grid[i][j] == '.') return
        left(grid, i, j-1)
        if (grid[i][j-1] == '.') {
            grid[i][j-1] = grid[i][j]
            grid[i][j] = '.'
        }
    }

    private fun left1(grid: MutableList<MutableList<Char>>, i: Int, j: Int) {
        if (grid[i][j] == '#' || grid[i][j] == '.') return
        left1(grid, i, j-1)
        if (grid[i][j-1] == '.') {
            grid[i][j-1] = grid[i][j]
            grid[i][j] = '.'
        }
    }


    private fun right(grid: MutableList<CharArray>, i: Int, j: Int) {
        if (grid[i][j] == '#' || grid[i][j] == '.') return
        right(grid, i, j+1)
        if (grid[i][j+1] == '.') {
            grid[i][j+1] = grid[i][j]
            grid[i][j] = '.'
        }
    }
    private fun right1(grid: MutableList<MutableList<Char>>, i: Int, j: Int) {
        if (grid[i][j] == '#' || grid[i][j] == '.') return
        right1(grid, i, j+1)
        if (grid[i][j+1] == '.') {
            grid[i][j+1] = grid[i][j]
            grid[i][j] = '.'
        }
    }
    override fun part2(data: List<String>): Long {
        var i = 0
        val grid = mutableListOf<MutableList<Char>>()
        while (i < data.size){
            if (data[i].isBlank()) break
            val l = mutableListOf<Char>()
            for (c in data[i]) {
                if (c == '@') {
                    l.add('@')
                    l.add('.')
                }
                if (c == '#') {
                    l.add('#')
                    l.add('#')
                }
                if (c == 'O') {
                    l.add('[')
                    l.add(']')
                }
                if (c == '.') {
                    l.add('.')
                    l.add('.')
                }
            }
            grid.add(l)
            i++
        }
        val moves = StringBuilder()
        while (i < data.size){
            moves.append(data[i])
            i++
        }
        var (x, y) = pos1(grid)
        for (m in moves) {
            when (m) {
                '<' -> {
                    left1(grid, x, y-1)
                    if (grid[x][y-1] == '.') {
                        grid[x][y-1] = grid[x][y]
                        grid[x][y] = '.'
                        y--
                    }
                }
                '^' -> {
                    top(grid, x-1, setOf(y))
                    if (grid[x - 1][y] == '.') {
                        grid[x - 1][y] = grid[x][y]
                        grid[x][y] = '.'
                        x--
                    }
                }
                '>' -> {
                    right1(grid, x, y+1)
                    if (grid[x][y+1] == '.') {
                        grid[x][y+1] = grid[x][y]
                        grid[x][y] = '.'
                        y++
                    }
                }
                'v' -> {
                    down(grid, x+1, setOf(y))
                    if (grid[x + 1][y] == '.') {
                        grid[x + 1][y] = grid[x][y]
                        grid[x][y] = '.'
                        x++
                    }
                }
            }
        }
        var count = 0L
        for (m in grid.indices) {
            for (n in grid[m].indices) {
                if (grid[m][n] == '[') {
                    count += (m * 100) + n
                }
            }
        }

        return count
    }
}