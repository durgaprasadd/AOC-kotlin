package year2024

import model.DaySolver

class Day2Solver: DaySolver<List<String>, Int> {

    override fun part1(data: List<String>): Int {
        val lines = data.map { line -> line.split(" ").map { it.toInt() } }
        var count = 0
        for (line in lines){
            if(increasing(line) && safeIncrease(line)){
                println(line)
                count++
                continue
            }

            if(decreasing(line) && safeDecrease(line)){
                count++
                continue
            }
        }
        return count
    }

    private fun increasing(line: List<Int>): Boolean {
        for (i in 1 until line.size){
            if(line[i] <= line[i - 1]){
                return false
            }
        }
        return true
    }

    private fun safeIncrease(line: List<Int>): Boolean {
        for (i in 1 until line.size){
            if(line[i]-line[i-1] > 3) {
                return false
            }
        }
        return true
    }

    private fun safeDecrease(line: List<Int>): Boolean {
        for (i in 1 until line.size){
            if(line[i-1]-line[i] > 3) {
                return false
            }
        }
        return true
    }
    private fun decreasing(line: List<Int>): Boolean {
        for (i in 1 until line.size){
            if(line[i] >= line[i - 1]){
                return false
            }
        }
        return true
    }

    override fun part2(data: List<String>): Int {
        val lines = data.map { line -> line.split(" ").map { it.toInt() } }
        var count = 0
        for (line in lines){
            if(increasing(line) && safeIncrease(line)){
                println(line)
                count++
                continue
            }

            if(decreasing(line) && safeDecrease(line)){
                count++
                continue
            }

            if(checkIncreasing(line)) {
                count++
                continue
            }
        }
        return count
    }

    private fun checkIncreasing(line: List<Int>): Boolean {
        for (i in line.indices){
            val curr = line.filterIndexed { index, _ -> index != i}
            if(increasing(curr) && safeIncrease(curr)){
                return true
            }

            if(decreasing(curr) && safeDecrease(curr)){
                return true
            }
        }
        return false
    }

}