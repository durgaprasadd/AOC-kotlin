package year2024

import model.DaySolver
import java.io.FileWriter

class Day14Solver : DaySolver<List<String>, Long> {

    override fun part1(data: List<String>): Long {
        val x = 101
        val y = 103
        val d = data.map {
            val (p,v) = it.split(" ").map { it.split("=")[1].split(",").map { it.toInt() } }
            Pair(
                p[0] to p[1],
                v[0] to v[1]
            )
        }.map { (p,v) ->
            p.first + (v.first * 100) to p.second + (v.second*100)
        }.map { (a,b) ->
            var i = a
            while (i < 0) {
                i += x
            }
            var j = b
            while (j < 0) {
                j += y
            }
            i%x to j%y
        }
        val res = IntArray(4){0}
        for ((i, j) in d) {
            if (i in 0 until x/2 && j in 0 until y/2) {
                res[0]++
            }
            if (i in 0 until x/2 && j in (y/2)+1 until y) {
                res[1]++
            }
            if (i in (x/2)+1 until x && j in 0 until y/2) {
                res[2]++
            }
            if (i in (x/2)+1 until x && j in (y/2)+1 until y) {
                res[3]++
            }
        }

        println(res.toList())
        println(d)
        var count = 0L
        count += (res[0] * res[1] * res[2] * res[3])
        return count
    }


    override fun part2(data: List<String>): Long {
        val x = 101
        val y = 103
        val d = data.map {
            val (p,v) = it.split(" ").map { it.split("=")[1].split(",").map { it.toInt() } }
            Pair(
                p[0] to p[1],
                v[0] to v[1]
            )
        }
        val f = FileWriter("res.txt")
        for (i in 7570 until 7575) {

            f.write("\n")
            f.write("\n")
            f.write("\n")
            f.write("\n")
            printGrid(d.map {(p,v) ->
                p.first+(v.first*i) to p.second+(v.second*i)
            }.map { (i,j)  ->
                var a = i
                while (a < 0) a += x
                var b = j
                while (b < 0) b += y
                a%x to b%y
            }, f)
        }
        f.close()
        println(d)
        var count = 0L
        return count
    }

    private fun printGrid(l: List<Pair<Int, Int>>, f: FileWriter) {
        val grid = Array(103) {
            CharArray(101) {' '}
        }
        for ((i, j) in l) {
            grid[j][i] = '■'
        }
        grid.forEach {
            f.write(it.joinToString(""))
            f.write("\n")
        }
    }
}