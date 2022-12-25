package year2022

import model.DaySolver
import java.util.*

class Day13Solver : DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): MutableList<Pair<List<Any>, List<Any>>> {
        val result = mutableListOf<Pair<List<Any>, List<Any>>>()
        var i = 0
        while (i < data.size) {
            var j = 0
            val s = Stack<MutableList<Any>>()
            while (j < data[i].length - 1) {
                if (data[i][j].isDigit()) {
                    var k = data[i][j].toString()
                    if (j + 1 < data[i].length && data[i][j + 1].isDigit()) {
                        k += data[i][j + 1]
                        j++
                    }
                    val curr = s.peek()
                    curr.add(k.toInt())
                } else if (data[i][j] == '[') {
                    s.add(mutableListOf())
                } else if (data[i][j] == ']') {
                    val curr = s.pop()
                    s.peek().add(curr)
                }
                j++
            }
            val x = s.pop()
            i++
            j = 0
            while (j < data[i].length - 1) {
                if (data[i][j].isDigit()) {
                    var k = data[i][j].toString()
                    if (j + 1 < data[i].length && data[i][j + 1].isDigit()) {
                        k += data[i][j + 1]
                        j++
                    }
                    val curr = s.peek()
                    curr.add(k.toInt())
                } else if (data[i][j] == '[') {
                    s.add(mutableListOf())
                } else if (data[i][j] == ']') {
                    val curr = s.pop()
                    s.peek().add(curr)
                }
                j++
            }
            val y = s.pop()
            result.add(x to y)
            i += 2
        }
        return result
    }

    override fun part1(data: List<String>): Int {
        val input = parse(data)
        var count = 0
        for (i in input.indices) {
            val res = compare(input[i].first, input[i].second)
            if (res == true) {
                count += i + 1
            }
        }
        return count
    }

    private fun compare(x: List<Any>, y: List<Any>): Boolean? {
        var i = 0
        while (i < x.size && i < y.size) {
            if (x[i] is Int && y[i] is Int) {
                if (x[i].toString().toInt() < y[i].toString().toInt()) {
                    return true
                } else if (x[i].toString().toInt() > y[i].toString().toInt()) {
                    return false
                }
            } else if (x[i] !is Int && y[i] !is Int) {
                val res = compare(x[i] as List<Any>, y[i] as List<Any>)
                if (res != null) {
                    return res
                }
            } else if (x[i] !is Int) {
                val res = compare(x[i] as List<Any>, listOf(y[i]))
                if (res != null) {
                    return res
                }
            } else {
                val res = compare(listOf(x[i]), y[i] as List<Any>)
                if (res != null) {
                    return res
                }
            }
            i++
        }
        if (x.size == y.size) {
            return null
        }
        return x.size < y.size
    }

    override fun part2(data: List<String>): Int {
        val input = parse(data).flatMap { listOf(it.first, it.second) }
        val first = listOf(listOf(2)) as List<Any>
        val second = listOf(listOf(6)) as List<Any>
        var count = 0
        var count1 = 0
        for (i in input) {
            val res = compare(i, first)
            val res1 = compare(i, second)
            if (res == true){
                count++
            }
            if (res1 == true){
                count1++
            }
        }
        return (count+1) * (count1+2)
    }

}