package year2024

import model.DaySolver

class Day24Solver : DaySolver<List<String>, Any> {

    override fun part1(data: List<String>): Any {
        println(data)
        var i = 0
        val values = mutableMapOf<String, Boolean>()
        while (data[i].isNotBlank()){
            val (x,y) = data[i].split(":").map { it.trim() }
            values[x] = y == "1"
            i++
        }
        i++
        val commands = mutableListOf<List<String>>()
        while (i < data.size) {
            commands.add(data[i].split(" ").map { it.trim() })
            i++
        }
        println(values)
        println(commands)
        var flag = true
        var prev = values.size
        while (flag) {
            for (c in commands) {
                if (values[c[0]] == null || values[c[2]] == null) continue
                when(c[1]) {
                    "OR" -> values[c[4]] = values[c[0]]!! or values[c[2]]!!
                    "AND" -> values[c[4]] = values[c[0]]!! and values[c[2]]!!
                    "XOR" -> values[c[4]] = values[c[0]]!! xor values[c[2]]!!
                }
            }
            flag = values.size > prev
            prev = values.size
        }

        return keyValue(values, 'z')
    }

    private fun keyValue(values: MutableMap<String, Boolean>, c: Char): Long {
        val keys = values.keys.filter { it.startsWith(c) }
        val res = CharArray(keys.size) {
            '0'
        }
        for (key in keys) {
            if (values[key]!!) {
                res[keys.size - 1 - key.substring(1).toInt()] = '1'
            }
        }
        println(keys)

        return res.joinToString("").toLong(2)
    }

    override fun part2(data: List<String>): Any {
        println(data)
        var i = 0
        val values = mutableMapOf<String, Boolean>()
        while (data[i].isNotBlank()){
            val (x,y) = data[i].split(":").map { it.trim() }
            values[x] = y == "1"
            i++
        }
        i++
        val commands = mutableListOf<List<String>>()
        while (i < data.size) {
            commands.add(data[i].split(" ").map { it.trim() })
            i++
        }
        println(values)
        println(commands)
        val x = keyValue(values, 'x')
        val y = keyValue(values, 'y')
        val z = x+y
        println(z.toString(2))
        println("51657025112326".toLong().toString(2))
        var flag = true
        var prev = values.size
        while (flag) {
            for (c in commands) {
                if (values[c[0]] == null || values[c[2]] == null) continue
                when(c[1]) {
                    "OR" -> values[c[4]] = values[c[0]]!! or values[c[2]]!!
                    "AND" -> values[c[4]] = values[c[0]]!! and values[c[2]]!!
                    "XOR" -> values[c[4]] = values[c[0]]!! xor values[c[2]]!!
                }
            }
            flag = values.size > prev
            prev = values.size
        }

        return solvePart2(data)
    }

    private enum class Operation { AND, OR, XOR }

    private data class Gate(val a: String, val b: String, val op: Operation, var c: String)

    private fun solvePart2(input: List<String>): Any {
        val (gates, registers) = parse(input)

        val nxz = gates.filter { it.c.first() == 'z' && it.c != "z45" && it.op != Operation.XOR }
        val xnz = gates.filter { it.a.first() !in "xy" && it.b.first() !in "xy" && it.c.first() != 'z' && it.op == Operation.XOR }

        for (i in xnz) {
            val b = nxz.first { it.c == gates.firstZThatUsesC(i.c) }
            val temp = i.c
            i.c = b.c
            b.c = temp
        }

        val falseCarry = (getWiresAsLong(registers, 'x') + getWiresAsLong(registers, 'y') xor run(gates, registers)).countTrailingZeroBits().toString()
        return (nxz + xnz + gates.filter { it.a.endsWith(falseCarry) && it.b.endsWith(falseCarry) }).map { it.c }.sorted().joinToString(",")
    }

    private fun List<Gate>.firstZThatUsesC(c: String): String? {
        val x = filter { it.a == c || it.b == c }
        x.find { it.c.startsWith('z') }?.let { return "z" + (it.c.drop(1).toInt() - 1).toString().padStart(2, '0') }
        return x.firstNotNullOfOrNull { firstZThatUsesC(it.c) }
    }

    private fun run(gates: List<Gate>, registers: MutableMap<String, Int>): Long {
        val exclude = HashSet<Gate>()

        while (exclude.size != gates.size) {
            val available = gates.filter { a ->
                a !in exclude && gates.none { b ->
                    (a.a == b.c || a.b == b.c) && b !in exclude
                }
            }
            for ((a, b, op, c) in available) {
                val v1 = registers.getOrDefault(a, 0)
                val v2 = registers.getOrDefault(b, 0)
                registers[c] = when (op) {
                    Operation.AND -> v1 and v2
                    Operation.OR -> v1 or v2
                    Operation.XOR -> v1 xor v2
                }
            }
            exclude.addAll(available)
        }

        return getWiresAsLong(registers, 'z')
    }

    private fun getWiresAsLong(registers: MutableMap<String, Int>, type: Char) = registers.filter { it.key.startsWith(type) }.toList().sortedBy { it.first }.map { it.second }.joinToString("").reversed().toLong(2)

    private fun parse(input: List<String>): Pair<MutableList<Gate>, MutableMap<String, Int>> {
        val (initial, path) = file(input)
        val gates = path.map { it.split(" ").let { Gate(it[0], it[2], Operation.valueOf(it[1]), it[4]) } }.toMutableList()
        val registers = initial.associate { it.split(": ").let { it.first() to it.last().toInt() } }
        return gates to registers.toMutableMap()
    }

    private fun file(data: List<String>): Pair<MutableList<String>, MutableList<String>> {
        var i = 0
        val values = mutableListOf<String>()
        while (data[i].isNotBlank()){
            values.add(data[i])
            i++
        }
        i++
        val commands = mutableListOf<String>()
        while (i < data.size) {
            commands.add(data[i])
            i++
        }

        return values to commands
    }
}