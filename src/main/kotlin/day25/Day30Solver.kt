package day25

import model.DaySolver
import java.util.*

class Day30Solver: DaySolver<List<String>, String> {

    private fun parse(data: List<String>): Array<Stack<Char>> {
        var i = 0
        val count = (data[i].length + 1)/4
        val stacks = Array(count){
            Stack<Char>()
        }
        while (data[i].contains("[")){
            for (j in 0 until count){
                if(data[i][j*4] == '['){
                    stacks[j].add(data[i][(j*4)+1])
                }
            }
            i++
        }
        i+=2
        for (j in 0 until count){
            val temp = Stack<Char>()
            while (stacks[j].isNotEmpty()){
                temp.add(stacks[j].pop())
            }
            stacks[j] = temp
        }
        while (i < data.size){
            val words = data[i].split(" ")
            val quantity = words[1].toInt()
            val from = words[3].toInt()-1
            val to = words[5].toInt()-1
            val temp = Stack<Char>()
            for (j in 0 until quantity){
                temp.add(stacks[from].pop())
            }
            while (temp.isNotEmpty()){
                stacks[to].add(temp.pop())
            }
            i++
        }
        return stacks
    }
    override fun part1(data: List<String>): String {
        return parse(data).map {
            it.pop()
        }.joinToString("")
    }

    override fun part2(data: List<String>): String {
        return ""
    }

}