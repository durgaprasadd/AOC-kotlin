package day25

import model.DaySolver
import kotlin.math.abs

class Day40Solver : DaySolver<List<String>, Long> {
    var y = 0;

    private fun parse(data: List<String>): List<Pair<Pair<Long, Long>, Pair<Long, Long>>> {
        return data.map { line ->
            val words = line.split(": closest beacon is at ")
            val (x, y) = words[0]
                .replace("Sensor at ", "")
                .replace("x=", "")
                .replace("y=", "")
                .split(",")
                .map { it.trim().toLong() }
            val (x1, y1) = words[1]
                .replace("x=", "")
                .replace("y=", "")
                .split(",")
                .map { it.trim().toLong() }
            Pair(Pair(x, y), Pair(x1, y1))
        }
    }

//    override fun part1(data: List<String>): Int {
//        val input = parse(data)
//        var minX = -100
//        var maxX = 100
//        var minY = -100
//        var maxY = 100
////        for ((s,b) in input){
////            minX = minOf(s.first, b.first, minX)
////            minY = minOf(s.second, b.second, minY)
////            maxX = maxOf(s.first, b.first, maxX)
////            maxY = maxOf(s.second, b.second, maxY)
////        }
//        val grid = Array(maxY-minY+1){
//            IntArray(maxX-minX+1){
//                0
//            }
//        }
//        for ((s,b) in input){
//           grid[s.second - minY][s.first - minX] = 1
//           grid[b.second - minY][b.first - minX] = 2
//        }
//
//        for ((s,b)  in input){
//            val x = s.first - minX
//            val y = s.second - minY
//            val x1 = b.first - minX
//            val y1 = b.second - minY
//            val diff = abs(x-x1) + abs(y-y1)
//            var start = 0
//            for (i in y-diff..y){
//                for (j in x-start..x+start){
//                    if (grid[i][j] == 0){
//                        grid[i][j] = -1
//                    }
//                }
//                start++
//            }
//
//            start = diff
//            for (i in y..y+diff){
//                for (j in x-start..x+start){
//                    if (grid[i][j] == 0){
//                        grid[i][j] = -1
//                    }
//                }
//                start--
//            }
//        }
//
//        println(grid[10-minY].toList().filter { it == -1 }.size)
//
//        return 0
//    }

    override fun part1(data: List<String>): Long {
        val input = parse(data)
//        val y = 10
        val result = mutableListOf<LongRange>()
        for ((s, b) in input) {
            val distance = abs(s.first - b.first) + abs(s.second - b.second)
            if (y !in s.second - distance..s.second + distance) {
                continue
            }
            val diff = distance - abs(s.second - y)
            result.add(s.first - diff..s.first + diff)
        }

        var res = mutableSetOf<LongRange>()
        for (rang in result) {
            var curr = rang
            val res1 = mutableSetOf<LongRange>()
            for (s in res) {
                if (curr.first in s || curr.last in s || s.first in curr || s.last in curr) {
                    curr = minOf(curr.first, s.first)..maxOf(curr.last, s.last)
                } else {
                    res1.add(s)
                }
            }
            res1.add(curr)
            res = res1
        }
        val ans = res.first()
        println(ans.last - ans.first)
        return ans.last - ans.first
    }

    override fun part2(data: List<String>): Long {
        val input = parse(data).map { (s, b) ->
            val distance = abs(s.first - b.first) + abs(s.second - b.second)
            s to distance
        }
        val posLines = mutableListOf<Long>()
        val negLines = mutableListOf<Long>()
        for ((s, d) in input) {
            negLines.add(s.first + s.second - d)
            negLines.add(s.first + s.second + d)
            posLines.add(s.first - s.second - d)
            posLines.add(s.first - s.second + d)
        }

        var pos = 0L
        var neg = 0L
        for (i in posLines.indices){
            for (j in i+1 until posLines.size){
                val a = posLines[i]
                val b = posLines[j]
                val c = negLines[i]
                val d = negLines[j]
                if (abs(a-b) == 2L){
                    pos = minOf(a, b) + 1
                }

                if (abs(c-d) == 2L){
                    neg = minOf(c, d) + 1
                }
            }
        }

        val x = (pos + neg)/2
        val y = (neg-pos)/2
        println(x)
        println(y)
        return (x * 4000000) + y
    }

}