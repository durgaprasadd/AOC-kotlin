package day25

import model.DaySolver

typealias Cell = Pair<Int,Int>
class Day42Solver : DaySolver<String, Long> {
    private val points = listOf(
        listOf((0 to 0), (1 to 0), (2 to 0), (3 to 0)),
        listOf((0 to 1), (1 to 0), (1 to 1), (1 to 2), (2 to 1)),
        listOf((0 to 0), (1 to 0), (2 to 0), (2 to 1), (2 to 2)),
        listOf((0 to 0), (0 to 1), (0 to 2), (0 to 3)),
        listOf((0 to 0), (0 to 1), (1 to 0), (1 to 1))
    )

    override fun part1(data: String): Long {
        return solve(2022, points, data)
    }

    override fun part2(data: String): Long {
        return solve(1000000000000L, points, data)
    }

    private fun solve(n: Long, points: List<List<Cell>>, data: String): Long {
        var count = n
        val cells = mutableSetOf<Cell>()
        val cycles = mutableMapOf<Triple<Int, Int, List<Cell>>, Pair<Int, Long>>()
        var jet = 0
        var height = 0
        var piece = 0
        var additional = 0L
        while (count > 0) {
            val (currJet, currPiece, currHeight) = place(cells, jet, piece, height, points, data)
            jet = currJet
            piece = currPiece
            height = currHeight
            count--
            val ground = groundShape(cells, height) ?: continue
            if (Triple(jet, piece, ground) in cycles) {
                val (oldmaxy, oldcount) = cycles[Triple(jet, piece, ground)]!!
                val dcount = oldcount - count
                val dmaxy = height - oldmaxy
                if (dcount < count) {
                    additional += dmaxy * (count / dcount)
                    count %= dcount
                }
            }

            cycles[Triple(jet, piece, ground)] = (height to count)
        }
        return additional + height
    }

    private fun groundShape(cells: MutableSet<Cell>, height: Int): List<Cell>? {
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

    private fun canMove(
        cells: MutableSet<Cell>,
        piece: Int,
        x: Int,
        y: Int,
        points: List<List<Cell>>
    ): Boolean {
        return points[piece].all { (dx, dy) ->
            free(cells, x + dx, y + dy)
        }
    }

    private fun free(t: MutableSet<Cell>, x: Int, y: Int): Boolean {
        return x in 0..6 && y > 0 && (x to y) !in t
    }

    private fun place(
        cells: MutableSet<Cell>,
        jet: Int,
        piece: Int,
        height: Int,
        points: List<List<Cell>>,
        data: String
    ): Triple<Int, Int, Int> {
        var x = 2
        var y = height + 5
        var currJet = jet
        while (canMove(cells, piece, x, y - 1, points)) {
            y--
            if (data[currJet] == '<' && canMove(cells, piece, x - 1, y, points)) x -= 1
            if (data[currJet] == '>' && canMove(cells, piece, x + 1, y, points)) x += 1
            currJet = (currJet + 1) % data.length
        }
        val newCells = points[piece].map { (dx, dy) ->
            x + dx to y + dy
        }
        cells.addAll(newCells)
        return Triple(currJet, (piece + 1) % points.size, maxOf(height, newCells.maxOf { it.second }))
    }

}