package year2022

import model.DaySolver

class Day3Solver: DaySolver<List<String>, Int> {

    val opp = mapOf(
        "A X" to 4,
        "A Y" to 8,
        "A Z" to 3,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 7,
        "C Y" to 2,
        "C Z" to 6
    )

    val opp1 = mapOf(
        "A X" to 3,
        "A Y" to 4,
        "A Z" to 8,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 2,
        "C Y" to 6,
        "C Z" to 7
    )
    private fun parse(data: List<String>): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        var curr = mutableListOf<Int>()
        for (i in data){
            if (i.isBlank()){
                result.add(curr)
                curr = mutableListOf<Int>()
            }else {
                curr.add(i.toInt())
            }
        }
        if (curr.isNotEmpty()){
            result.add(curr)
        }
        return result
    }

    override fun part1(data: List<String>): Int {
        var common = ""
        for(line in data){
            var first = 0
            var second = line.length/2
            val one = mutableSetOf<Char>()
            val two = mutableSetOf<Char>()
            val c = mutableSetOf<Char>()
            while(first < line.length/2){
                if (line[first] in two){
                    c.add(line[first])
                }
                one.add(line[first])
                if (line[second] in one){
                    c.add(line[second])
                }

                two.add(line[second])
                first++
                second++
            }
            for (i in c){
                common += i
            }
        }

        var result = 0
        for(c in common){
            if (c.isLowerCase()){
                result += 1 + (c-'a')
            }else {
                result += 27 + (c - 'A')
            }
        }
        return result
    }

    override fun part2(data: List<String>): Int {
        return data.chunked(3).map {
            comman(it)
        }.sumOf {
            if (it.isLowerCase()){
                1 + (it-'a')
            }else {
                27 + (it-'A')
            }
        }
    }

    private fun comman(lines: List<String>): Char {
        val first = lines[0].toSet()
        val second = lines[1].toSet()
        for (c in lines[2]){
            if (c in first && c in second){
                return c
            }
        }
        return 'a'
    }

    fun part3(s:Long, n: Long): Long {
        var result = 0L
        val combs = setOf(1L,100L, 10000L, 1000000L, 100000000L, 10000000000L, 1000000000000L)
        for (i in s..n){
            val prod = i*i
            if (prod in combs || sumOfDigits(prod.toString(), 0, i)){
                result += prod
            }
        }
        return result
    }

    private fun sumOfDigits(prod:String, start:Int, res:Long):Boolean {
        if (start == prod.length && res == 0L){
            return true
        }
        for (n in start+1..prod.length){
            if (sumOfDigits(prod, n, res - prod.substring(start, n).toLong())){
                return true
            }
        }
        return false
    }

}