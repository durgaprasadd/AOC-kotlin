package year2024

import model.DaySolver

class Day13Solver : DaySolver<List<String>, Long> {

    override fun part1(data: List<String>): Long {
        var count = 0L
        var i = 0
        while (i < data.size) {
            val a = data[i].split(":")[1].split(",")
            val b = data[i + 1].split(":")[1].split(",")
            val t = data[i + 2].split(":")[1].split(",")
            val ax = a[0].replace("X+", "").trim().toInt()
            val ay = a[1].replace("Y+", "").trim().toInt()
            val bx = b[0].replace("X+", "").trim().toInt()
            val by = b[1].replace("Y+", "").trim().toInt()
            val tx = t[0].replace("X=", "").trim().toInt()
            val ty = t[1].replace("Y=", "").trim().toInt()
            val res = solve(ax to ay, bx to by, tx to ty)
            if (res != -1) count += res
            i += 4
        }
        return count
    }

    private fun solve(a: Pair<Int, Int>, b: Pair<Int, Int>, t: Pair<Int, Int>): Int {
        val limit = minOf(
            maxOf(
                times(t.first, a.first),
                times(t.second, a.second)
            ), 100
        )
        var res = Int.MAX_VALUE
        for (i in 0..limit) {
            val cx = i * a.first
            val cy = i * a.second
            val j = maxOf(
                times(t.first - cx, b.first),
                times(t.second - cy, b.second)
            )
            if (j > 100) continue
            if (cx + j * b.first != t.first || cy + j * b.second != t.second) continue
            val curr = (i * 3) + j
            println("i $i j $j")
            res = minOf(curr, res)
        }
        return if (res == Int.MAX_VALUE) -1 else res
    }

    private fun solve(a: Pair<Long, Long>, b: Pair<Long, Long>, t: Pair<Long, Long>): Long {
        val tx = t.first
        val ty = t.second
        val ax = a.first
        val ay = a.second
        val bx = b.first
        val by = b.second
        var y = (ay * bx) - (by * ax)
        var t1 = (tx * ay) - (ty * ax)
        if(y < 0) {
           y *= -1
           t1 *= -1
        }
        if (t1%y != 0L) return -1
        val j = t1/y
        if ((tx - (bx * j))%ax != 0L) return -1
        val i = (tx - (bx * j))/ax
        return i*3 + j
    }

    private fun times(a: Int, b: Int): Int {
        return if (a % b == 0) a / b else (a / b) + 1
    }

    override fun part2(data: List<String>): Long {
        var count = 0L
        var i = 0
        while (i < data.size) {
            val a = data[i].split(":")[1].split(",")
            val b = data[i + 1].split(":")[1].split(",")
            val t = data[i + 2].split(":")[1].split(",")
            val ax = a[0].replace("X+", "").trim().toLong()
            val ay = a[1].replace("Y+", "").trim().toLong()
            val bx = b[0].replace("X+", "").trim().toLong()
            val by = b[1].replace("Y+", "").trim().toLong()
            val tx = t[0].replace("X=", "").trim().toLong() + 10000000000000L
            val ty = t[1].replace("Y=", "").trim().toLong() + 10000000000000L
            val res = solve(ax to ay, bx to by, tx to ty)
            if (res != -1L) count += res
            i += 4
        }
        return count
    }
}