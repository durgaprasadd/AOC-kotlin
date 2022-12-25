package year2022

import model.DaySolver

class Day1Solver: DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<Int> {
        val result = mutableListOf<Int>()
        var curr = 0
        for (i in data){
            if (i.isBlank()){
                result.add(curr)
                curr = 0
            }else {
                curr += i.toInt()
            }
        }
        if (curr != 0){
            result.add(curr)
        }
        return result
    }
    override fun part1(data: List<String>): Int {
        return parse(data).maxOrNull()!!
    }

    override fun part2(data: List<String>): Int {
        return parse(data).sorted().reversed().take(3).sum()
    }

}