package year2024

import model.DaySolver

class Day17Solver : DaySolver<List<String>, Long> {

    private var a = 0
    private var b = 0
    private var c = 0

    private fun operand(i: Int): Int {
        return when(i) {
            4 -> a
            5 -> b
            6 -> c
            7 -> throw Exception("Invalid operand")
            else -> i
        }
    }

    private fun pow(base: Int, power:Int): Int {
        if (power == 0) return 1
        if (power == 1) return base
        val curr = pow(base, power/2)
        return if (power%2 == 0) curr*curr else curr*curr*base
    }
    override fun part1(data: List<String>): Long {
        a = data[0].split(": ")[1].toInt()
        b = data[1].split(": ")[1].toInt()
        c = data[2].split(": ")[1].toInt()
        val commands = data[4].split(": ")[1].split(",").map { it.toInt() }
        var i = 0
        val res = mutableListOf<Int>()
        while (i < commands.size){
            when(commands[i]) {
                0 -> {
                    a /= pow(2, operand(commands[i + 1]))
                }
                1 -> {
                    b = b xor commands[i+1]
                }
                2 -> {
                    b = operand(commands[i+1])%8
                }
                3 -> {
                    if (a == 0) i +=0 else i = commands[i+1]-2
                }
                4 -> {
                    b = b xor c
                }
                5 -> {
                    res.add(operand(commands[i+1])%8)
                }
                6 -> {
                    b = a / pow(2, operand(commands[i + 1]))
                }
                7 -> {
                    c = a / pow(2, operand(commands[i + 1]))
                }
            }
            i += 2
        }
        var count = 0L
        println(res.joinToString(","))
        return count
    }


    private fun solve(x: Int, commands: List<Int>): Int {
        a = x
        var i = 0
        var j = 0
        while (i < commands.size){
            when(commands[i]) {
                0 -> {
                    a /= pow(2, operand(commands[i + 1]))
                }
                1 -> {
                    b = b xor commands[i+1]
                }
                2 -> {
                    b = operand(commands[i+1])%8
                }
                3 -> {
                    if (a == 0) i +=0 else i = commands[i+1]-2
                }
                4 -> {
                    b = b xor c
                }
                5 -> {
                    if (operand(commands[i+1])%8 != commands[j]) return -1
                    j++
                }
                6 -> {
                    b = a / pow(2, operand(commands[i + 1]))
                }
                7 -> {
                    c = a / pow(2, operand(commands[i + 1]))
                }
            }
            i += 2
        }
        return if (j == commands.size) 0 else -1
    }


    override fun part2(data: List<String>): Long {
        a = data[0].split(": ")[1].toInt()
        b = data[1].split(": ")[1].toInt()
        c = data[2].split(": ")[1].toInt()
        val commands = data[4].split(": ")[1].split(",").map { it.toInt() }
        var i = 2000000000
        while (i <= 4000000000){
            val curr = solve(i, commands)
            if (curr == 0) {
                println(i)
                println(curr)
                break
            }
            i++
        }
        var count = 0L
        return count
    }

}