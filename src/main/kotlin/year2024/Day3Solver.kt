package year2024

import model.DaySolver

class Day3Solver: DaySolver<List<String>, Int> {

    override fun part1(data: List<String>): Int {
        val set = mutableListOf<String>()
        for (i in 0 until 1000) {
            for (j in 0 until 1000) {
                set.add("mul($i,$j)")
            }
        }
        val result = mutableListOf<String>()
        data.forEach {
            var curr = it
            for (i in set){
                while (curr.contains(i)){
                    result.add(i)
                    curr = curr.replaceFirst(i,"")
                }
            }
        }
        var sum = 0
        for (i in result){
            val (f,s) = i.split(",")
            sum += f.replace("mul(","").toInt() * s.replace(")","").toInt()
        }
        return sum
    }


    override fun part2(data: List<String>): Int {
        val set = mutableSetOf<String>()
        for (i in 0 until 1000) {
            for (j in 0 until 1000) {
                set.add("mul($i,$j)")
            }
        }
        val result = mutableListOf<String>()
        var skip = false
        data.forEach {
            for (i in it.indices){
                if(i+4 < it.length && it.substring(i,i+4) == "do()"){
                    skip = false
                    continue
                }
                if(i+7 < it.length && it.substring(i,i+7) == "don't()"){
                    skip = true
                    continue
                }
                if(skip){
                    continue
                }
                for (j in 8 .. 12) {
                    if (i + j > it.length)
                        break
                    val curr = it.substring(i, i + j)
                    if (curr in set) {
                        result.add(curr)
                    }
                }
            }
        }
        var sum = 0
        for (i in result){
            val (f,s) = i.split(",")
            sum += f.replace("mul(","").toInt() * s.replace(")","").toInt()
        }
        return sum
    }

}