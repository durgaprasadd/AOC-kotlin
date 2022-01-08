package day24

import model.DaySolver

typealias Block = List<List<String>>

class Day24Solver : DaySolver<List<String>, Int> {

    companion object {
        val registers = listOf("w", "x", "y", "z")
    }

    private fun parseData(data: List<String>): List<Block> {
        return data.map {
            it.split(" ").map { it.trim() }.filter { it.isNotBlank() }
        }.chunked(18)
    }

    override fun part1(data: List<String>): Int {
        val parsedData = parseData(data)
        println(parsedData)
//        println(optCompute(parsedData))
        println(run(false,data.chunked(18)))
//        println(compute(parsedData, registers.map { it to 0 }.toMap()))
        return 1
    }

    private fun optCompute(blocks: List<Block>, z: Int = 0): Int {
        if (blocks.isEmpty()){
            return z
        }
        if (blocks.size > 7){
            println(blocks.size)
        }
        val block = blocks.first()
        for (w in 9 downTo 1) {
            var temp = z
            val something = (temp % 26) + block[5][2].toInt() != w
            temp /= block[4][2].toInt()
            if (something) {
                temp *= 26
                temp += w + block[15][2].toInt()
            }
            val result = optCompute(blocks.drop(1), temp)
            if (result == 0) {
                return 0
            }
        }
        return 1
    }

    private fun compute(
        commands: Block,
        data: Map<String, Int>,
        count: Int = 0,
        visited: MutableMap<Pair<Map<String, Int>, Int>, Pair<Int, String>> = mutableMapOf()
    ): Pair<Int, String> {
        val value = visited.getOrDefault(Pair(data, count), null)
        if (value != null) {
            return value
        }
        if (count < 4) {
            println(count)
        }
        for (i in 9 downTo 1) {
            var isDone = false
            var updatedData = data.toMutableMap()
            var remCommands = commands.toMutableList()
            var result = Pair(1, "")
            while (remCommands.isNotEmpty()) {
                val command = remCommands.first()
                when (command[0]) {
                    "inp" -> {
                        if (!isDone) {
                            updatedData[command[1]] = i
                            isDone = true
                        } else {
                            result = compute(remCommands, updatedData, count + 1, visited)
                            break
                        }
                    }
                    "add" -> {
                        if (command[2] in registers) {
                            updatedData[command[1]] = updatedData[command[1]]!! + updatedData[command[2]]!!
                        } else {
                            updatedData[command[1]] = updatedData[command[1]]!! + command[2].toInt()
                        }
                    }
                    "mul" -> {
                        if (command[2] in registers) {
                            updatedData[command[1]] = updatedData[command[1]]!! * updatedData[command[2]]!!
                        } else {
                            updatedData[command[1]] = updatedData[command[1]]!! * command[2].toInt()
                        }
                    }
                    "div" -> {
                        if (command[2] in registers) {
                            updatedData[command[1]] = updatedData[command[1]]!! / updatedData[command[2]]!!
                        } else {
                            updatedData[command[1]] = updatedData[command[1]]!! / command[2].toInt()
                        }
                    }
                    "mod" -> {
                        if (command[2] in registers) {
                            updatedData[command[1]] = updatedData[command[1]]!! % updatedData[command[2]]!!
                        } else {
                            updatedData[command[1]] = updatedData[command[1]]!! % command[2].toInt()
                        }
                    }
                    "eql" -> {
                        if (command[2] in registers) {
                            updatedData[command[1]] =
                                if (updatedData[command[1]]!! == updatedData[command[2]]!!) 1 else 0
                        } else {
                            updatedData[command[1]] = if (updatedData[command[1]]!! == command[2].toInt()) 1 else 0
                        }
                    }
                    else -> {
                        throw Exception("shouldn't come here")
                    }
                }
                remCommands.removeAt(0)
            }
            if (result.first == 0 || updatedData["z"] == 0) {
                visited[Pair(data, count)] = Pair(0, i.toString() + result.second)
                return Pair(0, i.toString() + result.second)
            }
        }
        visited[Pair(data, count)] = Pair(1, "")
        return Pair(1, "")
    }

    override fun part2(data: List<String>): Int {
        return 1
    }

    fun run(part2: Boolean, blocks: Block): Any {
        val result = MutableList(14) { -1 }
        val buffer = ArrayDeque<Pair<Int, Int>>()
        fun List<String>.lastOf(command: String) = last { it.startsWith(command) }.split(" ").last().toInt()
        val best = if (part2) 1 else 9
        blocks.forEachIndexed { index, instructions ->
            if ("div z 26" in instructions) {
                val offset = instructions.lastOf("add x")
                val (lastIndex, lastOffset) = buffer.removeFirst()
                val difference = offset + lastOffset
                if (difference >= 0) {
                    result[lastIndex] = if (part2) best else best - difference
                    result[index] = if (part2) best + difference else best
                } else {
                    result[lastIndex] = if (part2) best - difference else best
                    result[index] = if (part2) best else best + difference
                }
            } else buffer.addFirst(index to instructions.lastOf("add y"))
        }

        return result.joinToString("").toLong()
    }
}