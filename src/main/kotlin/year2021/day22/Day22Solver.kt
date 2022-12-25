package year2021.day22

import model.DaySolver


class Day22Solver : DaySolver<List<String>, Long> {
    var count = 0

    private fun parseData(data: List<String>): List<Pair<String, List<Pair<Int, Int>>>> {
        return data.map {
            val parts = it.trim().split(" ")
            parts[0].trim() to parts[1].split(",").map {
                val range = it.split("=")[1].split("..")
                range[0].toInt() to range[1].toInt()
            }
        }
    }

    private fun parseData1(data: List<String>): List<Pair<String, List<Pair<Long, Long>>>> {
        return data.map {
            val parts = it.trim().split(" ")
            parts[0].trim() to parts[1].split(",").map {
                val range = it.split("=")[1].split("..")
                range[0].toLong() to range[1].toLong()
            }
        }
    }

    override fun part1(data: List<String>): Long {
        val commands = parseData(data)
        val grid = (-50..50).map {
            (-50..50).map {
                (-50..50).map { "off" }.toMutableList()
            }
        }
        something(grid, commands)
        return grid.sumOf { plane ->
            plane.sumOf { line ->
                line.count { x ->
                    x == "on"
                }
            }
        }.toLong()
    }

    private fun something(grid: List<List<MutableList<String>>>, commands: List<Pair<String, List<Pair<Int, Int>>>>) {
        commands.forEach { (control, range) ->
            val minX = maxOf(range[0].first, -50)
            val maxX = minOf(range[0].second, 50)
            (minX..maxX).forEach { x ->
                val minY = maxOf(range[1].first, -50)
                val maxY = minOf(range[1].second, 50)
                (minY..maxY).forEach { y ->
                    val minZ = maxOf(range[2].first, -50)
                    val maxZ = minOf(range[2].second, 50)
                    (minZ..maxZ).forEach { z ->
                        grid[x + 50][y + 50][z + 50] = control
                    }
                }
            }
        }
    }

    override fun part2(data: List<String>): Long {
        val commands = parseData1(data)
        val grid = mutableMapOf<Pair<Long, Long>, MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, String>>>()
        commands.forEach {
            println(count++)
            updateGrid(grid, it.first, it.second[0], it.second[1], it.second[2])
        }
        val result = countLights(grid)
        println(result)
        return 0
    }

    private fun countLights(grid: MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, String>>>): Long {
        return grid.map { (key,value) ->
            val (x,y) = key
            ((y - x) + 1) * value.map { (key1,value1) ->
                val (x1,y1) = key1
                ((y1 - x1) +1) * value1.map { (key2,value2) ->
                    val (x2,y2) = key2
                    ((y2 - x2)+1) * if (value2 == "on") 1 else 0
                }.sum()
            }.sum()
        }.sum()
    }

    private fun updateGrid(
        grid: MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, String>>>,
        command: String,
        pair: Pair<Long, Long>,
        pair1: Pair<Long, Long>,
        pair2: Pair<Long, Long>
    ) {
        var (x1, y1) = pair
        val temp = grid.keys.filter { (x, y) -> includes(x, y, x1, y1) }.sortedBy {
            it.first
        }
        if (count > 418){
            println(temp)
        }
        if (temp.isEmpty()) {
            insertGrid(grid, command, pair, pair1, pair2)
            return
        }
        var done = true
        temp.forEach { key ->
            val (x, y) = key
            val value = grid[key]!!
            when {
                case1(x, y, x1, y1) -> {
                    updatePlane(value, command, pair1, pair2)
                    done = true
                }
                case2(x, y, x1, y1) -> {
                    grid.remove(key)
                    grid[Pair(x1,y1)] = value
                    grid[Pair(y1 + 1, y)] = clonePlane(value)
                    updatePlane(value, command, pair1, pair2)
                    done = true
                }
                case3(x, y, x1, y1) -> {
                    updatePlane(value, command, pair1, pair2)
                    x1 = y + 1
                    done = false
                }
                case4(x, x1, y1) -> {
                    grid.remove(key)
                    grid[Pair(x, x)] = value
                    grid[Pair(x + 1, y)] = clonePlane(value)
                    updatePlane(value, command, pair1, pair2)
                    insertGrid(grid, command, (x1 to x - 1), pair1, pair2)
                    done = true
                }
                case5(x, y, x1, y1) -> {
                    grid.remove(key)
                    grid[Pair(x, y1)] = value
                    grid[Pair(y1 + 1, y)] = clonePlane(value)
                    updatePlane(value, command, pair1, pair2)
                    insertGrid(grid, command, (x1 to x - 1), pair1, pair2)
                    done = true
                }
                case6(x, y, x1, y1) -> {
                    updatePlane(value, command, pair1, pair2)
                    insertGrid(grid, command, (x1 to x - 1), pair1, pair2)
                    done = true
                }
                case7(x, y, x1, y1) -> {
                    updatePlane(value, command, pair1, pair2)
                    insertGrid(grid, command, (x1 to x - 1), pair1, pair2)
                    x1 = y + 1
                    done = false
                }
                case8(x, y, x1, y1) -> {
                    grid.remove(key)
                    grid[Pair(x1, y1)] = value
                    grid[Pair(x, x1 - 1)] = clonePlane(value)
                    updatePlane(value, command, pair1, pair2)
                    done = true
                }
                case9(x, y, x1, y1) -> {
                    grid.remove(key)
                    grid[Pair(x1, y)] = value
                    grid[Pair(x, x1 - 1)] = clonePlane(value)
                    updatePlane(value, command, pair1, pair2)
                    x1 = y + 1
                    done = false
                }
                case10(y, x1, y1) -> {
                    grid.remove(key)
                    grid[Pair(x1, y)] = value
                    grid[Pair(x, x1 - 1)] = clonePlane(value)
                    updatePlane(value, command, pair1, pair2)
                    x1 = y + 1
                    done = false
                }
                case11(x, x1, y1) -> {
                    grid.remove(key)
                    grid[Pair(x1, x1)] = value
                    grid[Pair(x + 1, y)] = clonePlane(value)
                    updatePlane(value, command, pair1, pair2)
                    done = true
                }
                case12(y, x1, y1) -> {
                    grid.remove(key)
                    grid[Pair(x1, x1)] = value
                    grid[Pair(x, y - 1)] = clonePlane(value)
                    updatePlane(value, command, pair1, pair2)
                    done = true
                }
                case13(x, y, x1, y1) -> {
                    grid.remove(key)
                    grid[Pair(x1, y1)] = value
                    grid[Pair(x, x1 - 1)] = clonePlane(value)
                    grid[Pair(y1 + 1, y)] = clonePlane(value)
                    updatePlane(value, command, pair1, pair2)
                    done = true
                }
                else -> {
                    throw Exception("update grid")
                }
            }
        }

        if (!done) {
            insertGrid(grid, command, (x1 to y1), pair1, pair2)
        }
    }

    private fun updatePlane(
        plane: MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, String>>,
        command: String,
        pair1: Pair<Long, Long>,
        pair2: Pair<Long, Long>,
    ) {
        var (x1, y1) = pair1
        val temp = plane.keys.filter { (x, y) -> includes(x, y, x1, y1) }.sortedBy {
            it.first
        }
        if (temp.isEmpty()) {
            insertPlane(plane, command, pair1, pair2)
            return
        }
        var done = true
        temp.forEach { key ->
            val (x, y) = key
            val value = plane[key]!!
            when {
                case1(x, y, x1, y1) -> {
                    updateLine(value, command, pair2)
                    done = true
                }
                case2(x, y, x1, y1) -> {
                    plane.remove(key)
                    plane[Pair(x1,y1)] = value
                    plane[Pair(y1 + 1, y)] = value.toMap().toMutableMap()
                    updateLine(value, command, pair2)
                    done = true
                }
                case3(x, y, x1, y1) -> {
                    updateLine(value, command, pair2)
                    x1 = y + 1
                    done = false
                }
                case4(x, x1, y1) -> {
                    plane.remove(key)
                    plane[Pair(x, x)] = value
                    plane[Pair(x + 1, y)] = value.toMap().toMutableMap()
                    updateLine(value, command, pair2)
                    insertPlane(plane, command, (x1 to x - 1), pair2)
                    done = true
                }
                case5(x, y, x1, y1) -> {
                    plane.remove(key)
                    plane[Pair(x, y1)] = value
                    plane[Pair(y1 + 1, y)] = value.toMap().toMutableMap()
                    updateLine(value, command, pair2)
                    insertPlane(plane, command, (x1 to x - 1), pair2)
                    done = true
                }
                case6(x, y, x1, y1) -> {
                    updateLine(value, command, pair2)
                    insertPlane(plane, command, (x1 to x - 1), pair2)
                    done = true
                }
                case7(x, y, x1, y1) -> {
                    updateLine(value, command, pair2)
                    insertPlane(plane, command, (x1 to x - 1), pair2)
                    x1 = y + 1
                    done = false
                }
                case8(x, y, x1, y1) -> {
                    plane.remove(key)
                    plane[Pair(x1, y1)] = value
                    plane[Pair(x, x1 - 1)] = value.toMap().toMutableMap()
                    updateLine(value, command, pair2)
                    done = true
                }
                case9(x, y, x1, y1) -> {
                    plane.remove(key)
                    plane[Pair(x1, y)] = value
                    plane[Pair(x, x1 - 1)] = value.toMap().toMutableMap()
                    updateLine(value, command, pair2)
                    x1 = y + 1
                    done = false
                }
                case10(y, x1, y1) -> {
                    plane.remove(key)
                    plane[Pair(x1, y)] = value
                    plane[Pair(x, x1 - 1)] = value.toMap().toMutableMap()
                    updateLine(value, command, pair2)
                    x1 = y + 1
                    done = false
                }
                case11(x, x1, y1) -> {
                    plane.remove(key)
                    plane[Pair(x1, x1)] = value
                    plane[Pair(x + 1, y)] = value.toMap().toMutableMap()
                    updateLine(value, command, pair2)
                    done = true
                }
                case12(y, x1, y1) -> {
                    plane.remove(key)
                    plane[Pair(x1, x1)] = value
                    plane[Pair(x, y - 1)] = value.toMap().toMutableMap()
                    updateLine(value, command, pair2)
                    done = true
                }
                case13(x, y, x1, y1) -> {
                    plane.remove(key)
                    plane[Pair(x1, y1)] = value
                    plane[Pair(x, x1 - 1)] = value.toMap().toMutableMap()
                    plane[Pair(y1 + 1, y)] = value.toMap().toMutableMap()
                    updateLine(value, command, pair2)
                    done = true
                }
                else -> {
                    throw Exception("update plane")
                }
            }
        }

        if (!done) {
            insertPlane(plane, command, (x1 to y1), pair2)
        }
    }

    private fun updateLine(
        line: MutableMap<Pair<Long, Long>, String>,
        command: String,
        pair2: Pair<Long, Long>,
    ) {
        var (x1, y1) = pair2
        val temp = line.keys.filter { (x, y) -> includes(x, y, x1, y1) }.sortedBy {
            it.first
        }
        if (temp.isEmpty()) {
            insertLine(line, command, pair2)
            return
        }
        var done = true
        temp.forEach { key ->
            val (x, y) = key
            val value = line[key]!!
            when {
                case1(x, y, x1, y1) -> {
                    line[key] = command
                    done = true
                }
                case2(x, y, x1, y1) -> {
                    line.remove(key)
                    line[Pair(x1,y1)] = command
                    line[Pair(y1 + 1, y)] = value
                    done = true
                }
                case3(x, y, x1, y1) -> {
                    line[key] = command
                    x1 = y + 1
                    done = false
                }
                case4(x, x1, y1) -> {
                    line.remove(key)
                    line[Pair(x, x)] = command
                    line[Pair(x + 1, y)] = value
                    insertLine(line, command, (x1 to x - 1))
                    done = true
                }
                case5(x, y, x1, y1) -> {
                    line.remove(key)
                    line[Pair(x, y1)] = command
                    line[Pair(y1 + 1, y)] = value
                    insertLine(line, command, (x1 to x - 1))
                    done = true
                }
                case6(x, y, x1, y1) -> {
                    line[key] = command
                    insertLine(line, command, (x1 to x - 1))
                    done = true
                }
                case7(x, y, x1, y1) -> {
                    line[key] = command
                    insertLine(line, command, (x1 to x - 1))
                    x1 = y + 1
                    done = false
                }
                case8(x, y, x1, y1) -> {
                    line.remove(key)
                    line[Pair(x1, y1)] = command
                    line[Pair(x, x1 - 1)] = value
                    done = true
                }
                case9(x, y, x1, y1) -> {
                    line.remove(key)
                    line[Pair(x1, y)] = command
                    line[Pair(x, x1 - 1)] = value
                    x1 = y + 1
                    done = false
                }
                case10(y, x1, y1) -> {
                    line.remove(key)
                    line[Pair(x1, y)] = command
                    line[Pair(x, x1 - 1)] = value
                    x1 = y + 1
                    done = false
                }
                case11(x, x1, y1) -> {
                    line.remove(key)
                    line[Pair(x1, x1)] = command
                    line[Pair(x + 1, y)] = value
                    done = true
                }
                case12(y, x1, y1) -> {
                    line.remove(key)
                    line[Pair(x1, x1)] = command
                    line[Pair(x, y - 1)] = value
                    done = true
                }
                case13(x, y, x1, y1) -> {
                    line.remove(key)
                    line[Pair(x1, y1)] = command
                    line[Pair(x, x1 - 1)] = value
                    line[Pair(y1 + 1, y)] = value
                    done = true
                }

                else -> {
                    println("x $x y $y x1 $x1 y1 $y1")
                    throw Exception("update line")
                }
            }
        }
        if (!done) {
            insertLine(line, command, (x1 to y1))
        }
    }

    private fun insertGrid(
        grid: MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, String>>>,
        command: String,
        x: Pair<Long, Long>,
        y: Pair<Long, Long>,
        z: Pair<Long, Long>
    ) {
        grid[x] = mutableMapOf(
            y to mutableMapOf(
                z to command
            )
        )
    }

    private fun insertPlane(
        plane: MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, String>>,
        command: String,
        y: Pair<Long, Long>,
        z: Pair<Long, Long>
    ) {
        plane[y] = mutableMapOf(
            z to command
        )
    }

    private fun insertLine(
        line: MutableMap<Pair<Long, Long>, String>,
        command: String,
        z: Pair<Long, Long>
    ) {
        line[z] = command
    }

    private fun case1(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x == x1 && y == y1
    }

    private fun case2(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x == x1 && y > y1
    }

    private fun case3(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x == x1 && y < y1
    }

    private fun case4(x: Long, x1: Long, y1: Long): Boolean {
        return x == y1 && x1 < x
    }

    private fun case5(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x > x1 && y > y1
    }

    private fun case6(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x > x1 && y == y1
    }

    private fun case7(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x > x1 && y < y1
    }

    private fun case8(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x < x1 && y == y1
    }

    private fun case9(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x < x1 && y < y1
    }

    private fun case10(y: Long, x1: Long, y1: Long): Boolean {
        return y == x1 && y < y1
    }

    private fun case11(x: Long, x1: Long, y1: Long): Boolean {
        return x == x1 && x1 == y1
    }

    private fun case12(y: Long, x1: Long, y1: Long): Boolean {
        return y == x1 && x1 == y1
    }

    private fun case13(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x < x1 && y > y1
    }

    private fun includes(x: Long, y: Long, x1: Long, y1: Long): Boolean {
        return x in x1..y1 || y in x1..y1 || x1 in x..y || y1 in x..y
    }

    private fun clonePlane(value: Map<Pair<Long, Long>, Map<Pair<Long, Long>, String>>): MutableMap<Pair<Long, Long>, MutableMap<Pair<Long, Long>, String>> {
        val temp = mutableMapOf<Pair<Long, Long>, MutableMap<Pair<Long, Long>, String>>()
        value.forEach { (key, value) ->
            temp[key] = value.toMutableMap()
        }
     return temp
    }
}