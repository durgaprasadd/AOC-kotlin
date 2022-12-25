package year2022

import model.DaySolver

class Day6Solver: DaySolver<String, Int> {

    private fun parse(data: List<String>): List<List<Pair<Int, Int>>> {
        return data.map {
            it.split(",").map {
                val x = it.split("-")
                x[0].toInt()to x[1].toInt()
            }
        }
    }
    override fun part1(data: String): Int {
        for (i in 0 until data.length-3){
            val a = setOf(data[i], data[i+1], data[i+2], data[i+3])
            if (a.size == 4){
                return i+4
            }
        }
        return -1
    }

    override fun part2(data:String): Int {
        for (i in 0 until data.length-3){
            val a = mutableSetOf<Char>()
            for (j in i..i+13){
                a.add(data[j])
            }
            if (a.size == 14){
                return i+14
            }
        }
        return -1
    }

}