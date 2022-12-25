package year2022

import model.DaySolver
import java.util.*
import kotlin.math.abs

class Day18Solver: DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<Triple<Int, Int, Int>> {
        return data.map {
            val (x,y,z) = it.split(",").map {
               it.toInt()
            }
            Triple(x, y, z)
        }
    }

    override fun part1(data: List<String>): Int {
        val input = parse(data)
        var res = input.size * 6
        for (i in 0 until input.size-1){
            for (j in i+1 until input.size){
                if(isSameSide(input[i], input[j])){
                    res -= 2
                }
            }
        }
        return res
    }

    private fun isSameSide(triple: Triple<Int, Int, Int>, triple1: Triple<Int, Int, Int>): Boolean {
        val (x, y, z) = triple
        val (x1, y1, z1) = triple1
        return (x == x1 && y == y1 && abs(z-z1) == 1) || (z == z1 && y == y1 && abs(x-x1) == 1) || (x == x1 && z == z1 && abs(y-y1) == 1)
    }

    override fun part2(data: List<String>): Int {
        val input = parse(data).toSet()
        val max = input.maxOf { it.toList().maxOrNull()!! }
        val min = input.minOf { it.toList().minOrNull()!! }
        var res = 0
        for (pos in input){
            res += getCoord(pos).filter {
                exposed(it, input, max, min)
            }.size
        }
        return res
    }

    private fun getCoord(pos: Triple<Int, Int, Int>): List<Triple<Int, Int, Int>> {
        val (x,y,z) = pos
        return listOf(
            Triple(x-1,y,z),
            Triple(x+1,y,z),
            Triple(x,y-1,z),
            Triple(x,y+1,z),
            Triple(x,y,z-1),
            Triple(x,y,z+1),
        )
    }

    private fun exposed(pos: Triple<Int, Int, Int>, input: Set<Triple<Int, Int, Int>>, max: Int, min: Int):Boolean {
        if (pos in input){
            return false
        }
        val visited = mutableSetOf<Triple<Int,Int,Int>>()
        val stack = Stack<Triple<Int,Int,Int>>()
        stack.add(pos)
        while (stack.isNotEmpty()){
            val curr = stack.pop()
            if (curr in input || curr in visited){
                continue
            }
            visited.add(curr)
            val (x,y,z) = curr
            if (x !in min..max || y !in min..max || z !in min..max){
                return true
            }
            stack.addAll(getCoord(curr))
        }
        return false
    }
}