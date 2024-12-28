package year2024

import model.DaySolver

class Day19Solver : DaySolver<List<String>, Any> {

    override fun part1(data: List<String>): Long {
        val patterns = data[0].split(",").map { it.trim() }.toSet()
        println(patterns)
        var count = 0L
        for (i in 2 until data.size){
            if (valid(data[i].trim(), patterns, 0)){
                count++
            }
        }
        return count
    }

    private fun valid(s: String, patterns: Set<String>, i: Int):Boolean {
        if (i == s.length) return true
        if(s.substring(i) in patterns) return true
        for (k in i+1 until s.length) {
            if (s.substring(i, k) in patterns && valid(s, patterns, k)) {
                return true
            }
        }
        return false
    }

    private fun solve(s: String, patterns: Set<String>, i: Int, memo: MutableMap<String, Long>):Long {
        if (i == s.length) return 1
        if(memo[s.substring(i)] != null) return memo[s.substring(i)]!!
        var count = 0L
        for (k in i+1 .. s.length) {
            if (s.substring(i,k) !in patterns) continue
            val curr = solve(s, patterns, k, memo)
            if (curr != -1L) {
                count += curr
            }
        }
        if (count == 0L) count = -1L
        memo[s.substring(i)] = count
        return count
    }


    override fun part2(data: List<String>): Long {
        val patterns = data[0].split(",").map { it.trim() }.toSet()
        println(patterns)
        var count = 0L
        for (i in 2 until data.size){
            val curr = solve(data[i].trim(), patterns, 0, mutableMapOf())
            if (curr != -1L) {
                count+= curr
            }
        }
        return count
    }

}