package year2024

import model.DaySolver

class Day21Solver : DaySolver<List<String>, Any> {

    /* 029A
+---+---+---+
| 7 | 8 | 9 |
+---+---+---+
| 4 | 5 | 6 |
+---+---+---+
| 1 | 2 | 3 |
+---+---+---+
    | 0 | A |
    +---+---+
<vA <A A >>^A vA A  <^A >A <v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
v    < < A    >  >  ^  A    <A >A vA <^A A >A <vA A A >^A
<    A     ^  A  >  ^  ^ A   v  v v A
0 2 9 A
    +---+---+
    | ^ | A |
+---+---+---+
| < | v | > |
+---+---+---+
     */

    override fun part1(data: List<String>): Long {
        return solve(data, 3)
    }


    override fun part2(data: List<String>): Long {
        return solve(data, 26)
    }

    private fun genSteps(map: List<List<Char>>, start: Char, end: Char, visitedButtons: String): List<String> {
        if (start == end) return listOf("A")
        val startRow = map.indexOfFirst { it.contains(start) }
        val startCol = map[startRow].indexOf(start)
        val results = mutableListOf<String>()
        for (d in Direction.values()) {
            val nextRow = startRow + d.dr
            val nextCol = startCol + d.dc
            if (
                nextRow in map.indices &&
                nextCol in map[nextRow].indices &&
                map[nextRow][nextCol] != 'X' &&
                !visitedButtons.contains(map[nextRow][nextCol])
            ) {
                results.addAll(
                    genSteps(map, map[nextRow][nextCol], end, visitedButtons + start).map { d.chr + it }
                )
            }
        }
        return results
    }

    fun solve(data:List<String>, level: Int): Long {
        val mapDir = listOf(
            listOf('X', '^', 'A'),
            listOf('<', 'v', '>'),
        )
        val stepsDir = mutableMapOf<Pair<Char, Char>, List<String>>()
        for (start in "^A<v>") {
            for (end in "^A<v>") {
                stepsDir[start to end] = genSteps(mapDir, start, end, "")
            }
        }

        val mapNum =
            listOf(
                listOf('7', '8', '9'),
                listOf('4', '5', '6'),
                listOf('1', '2', '3'),
                listOf('X', '0', 'A'),
            )
        val stepsNum = mutableMapOf<Pair<Char, Char>, List<String>>()
        for (start in "7894561230A") {
            for (end in "7894561230A") {
                stepsNum[start to end] = genSteps(mapNum, start, end, "")
            }
        }

        data class State(val prev: Char, val curr: Char, val moreLevels: Int)
        val cache = mutableMapOf<State, Long>()

        fun press(map: Map<Pair<Char, Char>, List<String>>, state: State): Long {
            if (cache.containsKey(state)) return cache[state]!!

            if (state.moreLevels == 0) return 1

            var best = Long.MAX_VALUE
            for (steps in map[state.prev to state.curr]!!) {
                var value = 0L
                var prev = 'A'
                for (c in steps) {
                    value += press(stepsDir, State(prev, c, state.moreLevels - 1))
                    prev = c
                }
                if (value < best) best = value
            }
            cache[state] = best
            return best
        }

        var sum = 0L
        for (code in data) {
            var value = 0L
            var prevchr = 'A'
            for (c in code) {
                value += press(stepsNum, State(prevchr, c, level))
                prevchr = c
            }
            val complexity = value * code.substring(0, code.length - 1).toLong()
            sum += complexity
        }
        return sum
    }

    private enum class Direction(val dr: Int, val dc: Int, val chr: Char) {
        UP(-1, 0, '^'),
        DOWN(1, 0, 'v'),
        LEFT(0, -1, '<'),
        RIGHT(0, 1, '>'),
    }
}





