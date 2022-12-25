package year2022

import model.DaySolver

class Day9Solver: DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<Pair<String, Int>> {
        return data.map {
                val x = it.split(" ")
                x[0] to x[1].toInt()
        }
    }
    override fun part1(data: List<String>): Int {
        return solve(data, 2)
    }

    private fun next(x:Int, y:Int, x1:Int, y1:Int):Pair<Int,Int>{
        var x2 = x1
        var y2 = y1
        if ((abs(x, x1) == 1 && abs(y, y1) == 2) || (abs(x, x1) == 2 && abs(y, y1) == 1)){
            x2 += if (x > x1) 1 else -1
            y2 += if (y > y1) 1 else -1
        }else if (abs(x, x1) > 1 || abs(y, y1) > 1){
            x2 += if (x > x1) 1 else if (x == x1) 0 else -1
            y2 += if (y > y1) 1 else if (y == y1) 0 else -1
        }
        return x2 to y2
    }
    private fun abs(a:Int, b:Int):Int {
        return if (a > b) a - b else b-a
    }

    private fun solve(data: List<String>, knots:Int):Int {
        val commands = parse(data)
        val knotx = IntArray(knots){0}
        val knoty = IntArray(knots){0}
        val positions = mutableSetOf(0 to 0)
        val moveY = mapOf("R" to 1, "L" to -1, "U" to 0, "D" to 0)
        val moveX = mapOf("R" to 0, "L" to 0, "U" to -1, "D" to 1)
        for ((c, q) in commands){
            val currX = moveX[c]!!
            val currY = moveY[c]!!
            repeat(q){
                knotx[0] += currX
                knoty[0] += currY
                for (i in 1 until knots){
                    val (x,y) = next(knotx[i-1], knoty[i-1], knotx[i], knoty[i])
                    if (x == knotx[i] && y == knoty[i]){
                        break
                    }
                    knotx[i] = x
                    knoty[i] = y
                }
                positions.add(knotx[knots-1] to knoty[knots-1])
            }
        }
        return positions.size
    }
    override fun part2(data: List<String>): Int {
        return solve(data, 10)
    }

}