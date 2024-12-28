package year2024

import model.DaySolver

class Day5Solver : DaySolver<List<String>, Int> {

    override fun part1(data: List<String>): Int {
        var i = 0
        val order = mutableSetOf<Pair<Int,Int>>()
        while(i < data.size){
            if (data[i] == "") break
            val (x,y) = data[i].split("|").map { it.toInt() }
            order.add(x to y)
            i++
        }
        i++
        var res = 0
        while (i < data.size) {
            val row = data[i].split(",").map { it.toInt() }
            var flag = false
            for (j in 0 until row.size-1) {
                for (k in j+1 until row.size) {
                    if(row[k] to row[j] in order) {
                        flag = true
                        break
                    }
                }
                if (flag) break
            }
            if (!flag) {
                res += row[row.size/2]
            }
            i++
        }

        return res
    }


    override fun part2(data: List<String>): Int {
        var i = 0
        val order = mutableSetOf<Pair<Int,Int>>()
        while(i < data.size){
            if (data[i] == "") break
            val (x,y) = data[i].split("|").map { it.toInt() }
            order.add(x to y)
            i++
        }
        i++
        var res = 0
        while (i < data.size) {
            val row = data[i].split(",").map { it.toInt() }.toMutableList()
            var flag = false
            for (j in 0 until row.size-1) {
                for (k in j+1 until row.size) {
                    if(row[k] to row[j] in order) {
                        flag = true
                        val temp = row[k]
                        row[k] = row[j]
                        row[j] = temp
                    }
                }
            }
            if (flag) res += row[row.size/2]
            i++
        }

        return res
    }

}