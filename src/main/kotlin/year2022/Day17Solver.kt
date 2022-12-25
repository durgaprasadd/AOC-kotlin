package year2022

import model.DaySolver

typealias Cell = Pair<Int, Int>
typealias Integer = MutableValue<Int>

class Day17Solver : DaySolver<String, Long> {
    private val points = listOf(
        listOf((0 to 0), (1 to 0), (2 to 0), (3 to 0)),
        listOf((0 to 1), (1 to 0), (1 to 1), (1 to 2), (2 to 1)),
        listOf((0 to 0), (1 to 0), (2 to 0), (2 to 1), (2 to 2)),
        listOf((0 to 0), (0 to 1), (0 to 2), (0 to 3)),
        listOf((0 to 0), (0 to 1), (1 to 0), (1 to 1))
    )

    override fun part1(data: String): Long {
        return solve(2022, data)
    }

    override fun part2(data: String): Long {
        return solve(1000000000000L, data)
    }

    private fun solve(n: Long, data: String): Long {
        var count = n
        val cells = mutableSetOf<Cell>()
        val cycles = mutableMapOf<Triple<Int, Int, List<Cell>>, Pair<Int, Long>>()
        val jet = Integer(0)
        val height = Integer(0)
        val piece = Integer(0)
        var additional = 0L
        while (count > 0) {
            place(cells, jet, piece, height, data)
            count--
            val ground = groundShape(cells, height.value) ?: continue
            if (Triple(jet.value, piece.value, ground) in cycles) {
                val (oldHeight, oldCount) = cycles[Triple(jet.value, piece.value, ground)]!!
                val deltaCount = oldCount - count
                val deltaHeight = height.value - oldHeight
                if (deltaCount < count) {
                    additional += deltaHeight * (count / deltaCount)
                    count %= deltaCount
                }
            }
            cycles[Triple(jet.value, piece.value, ground)] = (height.value to count)
        }
        return additional + height.value
    }

    private fun groundShape(cells: Set<Cell>, height: Int): List<Cell>? {
        fun dfs(x: Int, y: Int, visited: MutableSet<Cell>) {
            if (!free(cells, x, y + height) || (x to y) in visited || visited.size > 20) return
            visited.add(x to y)
            for ((nx, ny) in listOf((x - 1 to y), (x + 1 to y), (x to y - 1))) {
                dfs(nx, ny, visited)
            }
        }

        val state = mutableSetOf<Cell>()
        for (x in 0 until 7) {
            dfs(x, 0, state)
        }
        return if (state.size < 20) state.toList() else null
    }

    private fun canMove(cells: Set<Cell>, piece: Int, x: Int, y: Int): Boolean {
        return points[piece].all { (dx, dy) ->
            free(cells, x + dx, y + dy)
        }
    }

    private fun free(cells: Set<Cell>, x: Int, y: Int): Boolean {
        return x in 0..6 && y > 0 && (x to y) !in cells
    }

    private fun place(cells: MutableSet<Cell>, jet: Integer, piece: Integer, height: Integer, data: String) {
        var x = 2
        var y = height.value + 5
        while (canMove(cells, piece.value, x, y - 1)) {
            y--
            if (data[jet.value] == '<' && canMove(cells, piece.value, x - 1, y)) x -= 1
            if (data[jet.value] == '>' && canMove(cells, piece.value, x + 1, y)) x += 1
            jet.value = (jet.value + 1) % data.length
        }
        val newCells = points[piece.value].map { (dx, dy) ->
            x + dx to y + dy
        }
        cells.addAll(newCells)
        height.value = maxOf(height.value, newCells.maxOf { it.second })
        piece.value = (piece.value + 1) % points.size
    }

}

data class MutableValue<T>(var value: T)