package day19

import model.DaySolver
import kotlin.math.abs


class Day19Solver : DaySolver<List<String>, Long> {

    private fun parseData(data: List<String>): MutableList<MutableList<Triple<Int, Int, Int>>> {
        val parsedData = mutableListOf<MutableList<Triple<Int, Int, Int>>>()
        data.forEach { line ->
            if (line.contains("---")) {
                parsedData.add(mutableListOf())
            } else if (line.trim().isNotBlank()) {
                val position = line.split(",").map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }
                parsedData.last().add(Triple(position[0], position[1], position[2]))
            }
        }
        return parsedData
    }

    override fun part1(data: List<String>): Long {
        println(data)
        val parsedData = parseData(data)
        println(parsedData.size)
        something1(parsedData)
        println(parsedData.flatten().fold(emptyList<Triple<Int, Int, Int>>()) { acc, triple ->
            if (acc.contains(triple)) acc else acc.plus(triple)
        }.size)
        return 0
    }

    private fun something(data: List<List<Triple<Int, Int, Int>>>) {
        for (i in data.indices) {
            for (j in i + 1 until data.size) {
                try {
                    relativePoints(data[i], data[j])
                    println("done with $i and $j")
                } catch (e: Throwable) {
                    println("failed with $i and $j")
                }
            }
        }
    }

    private fun something1(
        data: MutableList<MutableList<Triple<Int, Int, Int>>>,
        visited: MutableList<Int> = mutableListOf(0),
        point: Int = 0
    ) {
        println(visited)
        data.indices.filter { index -> !visited.contains(index) }.forEach { index ->
            if (!visited.contains(index)) {
                val scanner = data[index]
                val result = relativePoints(data[point], data[index])
                if (result != null) {
                    data[index] = scanner.map { point ->
                        val (diff, pos) = result
                        val (x1, y1, z1) = diff
                        val (i, j, k) = pos
                        val x = point.toList()[i]
                        val y = point.toList()[j]
                        val z = point.toList()[k]
                        Triple(
                            (x * x1.first) + x1.second,
                            (y * y1.first) + y1.second,
                            (z * z1.first) + z1.second
                        )
                    }.toMutableList()
                    visited.add(index)
                    something1(data, visited, index)
                }
            }
        }
    }

    private fun relativePoints(
        scanner0: List<Triple<Int, Int, Int>>,
        scanner1: List<Triple<Int, Int, Int>>
    ): Pair<Triple<Pair<Int, Int>, Pair<Int, Int>, Pair<Int, Int>>, Triple<Int, Int, Int>>? {
        for (position0 in scanner0) {
            for (position1 in scanner1) {
                val orientations = listOf(
                    Triple(-1, -1, -1),
                    Triple(-1, -1, 1),
                    Triple(-1, 1, -1),
                    Triple(-1, 1, 1),
                    Triple(1, -1, -1),
                    Triple(1, -1, 1),
                    Triple(1, 1, -1),
                    Triple(1, 1, 1)
                )
                val positions = listOf(
                    Triple(0, 1, 2),
                    Triple(0, 2, 1),
                    Triple(1, 0, 2),
                    Triple(1, 2, 0),
                    Triple(2, 0, 1),
                    Triple(2, 1, 0)
                )
                val differences = positions.flatMap { (i, j, k) ->
                    orientations.map { (x, y, z) ->
                        val x1 = position1.toList()[i]
                        val y1 = position1.toList()[j]
                        val z1 = position1.toList()[k]
                        val (x2, y2, z2) = position0
                        Pair(
                            Triple(
                                Pair(x, x2 - (x1 * x)),
                                Pair(y, y2 - (y1 * y)),
                                Pair(z, z2 - (z1 * z)),
                            ),
                            Triple(i, j, k)
                        )
                    }
                }
                differences.forEach { difference ->
                    val size = scanner1.map { point ->
                        val (diff, pos) = difference
                        val (x1, y1, z1) = diff
                        val (i, j, k) = pos
                        val x = point.toList()[i]
                        val y = point.toList()[j]
                        val z = point.toList()[k]
                        Triple(
                            (x * x1.first) + x1.second,
                            (y * y1.first) + y1.second,
                            (z * z1.first) + z1.second
                        )
                    }.filter { scanner0.contains(it) }.size
                    if (size >= 12) {
                        return difference
                    }
                }
            }
        }
        return null
    }

    private fun something2(
        data: MutableList<MutableList<Triple<Int, Int, Int>>>,
        scannerPositions: MutableList<Triple<Int, Int, Int>>,
        visited: MutableList<Int> = mutableListOf(0),
        point: Int = 0
    ) {
        println(visited)
        data.indices.filter { index -> !visited.contains(index) }.forEach { index ->
            if (!visited.contains(index)) {
                val scanner = data[index]
                val result = relativePoints(data[point], data[index])
                if (result != null) {
                    data[index] = scanner.map { point ->
                        val (diff, pos) = result
                        val (x1, y1, z1) = diff
                        val (i, j, k) = pos
                        val x = point.toList()[i]
                        val y = point.toList()[j]
                        val z = point.toList()[k]
                        Triple(
                            (x * x1.first) + x1.second,
                            (y * y1.first) + y1.second,
                            (z * z1.first) + z1.second
                        )
                    }.toMutableList()
                    val (diff, _) = result
                    val (x1, y1, z1) = diff
                    scannerPositions[index] = Triple(
                         x1.second,
                        y1.second,
                        z1.second
                    )
                    visited.add(index)
                    something2(data, scannerPositions, visited, index)
                }
            }
        }
    }

    override fun part2(data: List<String>): Long {
        println(data)
        val parsedData = parseData(data)
        println(parsedData.size)
        val scannerPositions = parsedData.map { Triple(0, 0, 0) }.toMutableList()
        something2(parsedData, scannerPositions)
        println(scannerPositions)
        var maxD = 0
        for (i in scannerPositions.indices){
            for( j in i+1 until scannerPositions.size){
                val (x,y,z) = scannerPositions[i]
                val (x1,y1,z1) = scannerPositions[j]
                val d = abs(x-x1) + abs(y-y1) + abs(z-z1)
                if (maxD < d){
                    maxD = d
                }
            }
        }
        return maxD.toLong()
    }
}