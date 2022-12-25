package year2022

import model.DaySolver

class Day21Solver: DaySolver<List<String>, Long> {

    private fun parse(data: List<String>): Map<String, String> {
        return data.map {
            val words = it.split(": ")
            words[0] to words[1]
        }.toMap()
    }
    override fun part1(data: List<String>): Long {
        val input = parse(data)
        return findNumber(input, "root")
    }

    private fun findNumber(input: Map<String, String>, name: String): Long {
        val curr = input[name]!!
        if (curr.toLongOrNull() != null){
            return curr.toLong()
        }
        val commands = curr.split(" ")
        val value1 = findNumber(input, commands[0])
        val value2 = findNumber(input, commands[2])
        return when(commands[1].trim()){
            "+" -> value1 + value2
            "-" -> value1 - value2
            "*" -> value1 * value2
            else -> value1 / value2
        }
    }

    override fun part2(data: List<String>): Long {
        val input = parse(data)
        val curr = input["root"]!!
        val commands = curr.split(" ")
        return findHumanValue(input, commands[0], findNumber(input, commands[2]))!!
    }

    private fun findHumanValue(input: Map<String, String>, name: String, value: Long):Long? {
        if (name == "humn"){
            return value
        }
        val curr = input[name]!!.split(" ")
        val value1 = isHuman(input, curr[0])
        val value2 = isHuman(input, curr[2])
        if (value1 && value2){
            println("both ways")
            return null
        }
        if (value1){
            var newValue = value
            val newValue2 = findNumber(input, curr[2])
            when(curr[1].trim()){
                "+" -> newValue = value - newValue2
                "-" -> newValue = value + newValue2
                "*" -> newValue = value / newValue2
                "/" -> newValue = value * newValue2
            }
            return findHumanValue(input, curr[0], newValue)
        }

        if (value2){
            var newValue = value
            val newValue2 = findNumber(input, curr[0])
            when(curr[1].trim()){
                "+" -> newValue = value - newValue2
                "-" -> newValue =  newValue2 - value
                "*" -> newValue = value / newValue2
                "/" -> newValue = newValue2/ value
            }
            return findHumanValue(input, curr[2], newValue)
        }
        println("no way")
        return null
    }


    private fun isHuman(input: Map<String, String>, name: String): Boolean {
        if (name == "humn"){
            return true
        }
        val curr = input[name]!!
        if (curr.toLongOrNull() != null){
            return false
        }
        val commands = curr.split(" ")
        val value1 = isHuman(input, commands[0])
        val value2 = isHuman(input, commands[2])
        return value1 || value2
    }
}