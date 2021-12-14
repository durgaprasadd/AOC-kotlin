package day14

import model.DaySolver

class Day14Solver : DaySolver<List<String>, Long> {

    private fun parseData(data: List<String>): Pair<String, Map<String, String>> {
        val input = data.first()
        val relations = data.drop(2)
            .map { it.split("->") }
            .map { it[0].trim() to it[1].trim() }
            .fold(emptyMap<String, String>()) { acc, pair ->
                acc.plus(pair)
            }
        return input to relations
    }

    override fun part1(data: List<String>): Long {
        val (input, relations) = parseData(data)
        var result = input
        for (i in 0 until 10) {
            result = result.windowed(2).map { it[0] + relations[it]!! }.plus(result.last()).joinToString("")
        }
        val output = result.groupBy { c -> c }
        return (output.values.maxOf { x -> x.size } - output.values.minOf { x -> x.size }).toLong()
    }

    override fun part2(data: List<String>): Long {
        val (input, relations) = parseData(data)
        var result = input.windowed(2).fold(emptyMap<String, Long>()) { acc, s ->
            acc.plus(Pair(s, acc.getOrDefault(s, 0) + 1))
        }
        for (i in 0 until 40) {
            val temp = mutableMapOf<String, Long>()
            result.forEach { key, value ->
                val letter = relations[key]!!
                val first = key[0] + letter
                val second = letter + key[1]
                temp[first] = temp.getOrDefault(first, 0) + value
                temp[second] = temp.getOrDefault(second, 0) + value
            }
            result = temp
        }
        val output = result.keys.fold(emptyMap<Char, Long>()) { acc, s ->
            val value = result[s]!!
            acc.plus(Pair(s[1], acc.getOrDefault(s[1], 0) + value))
        }
        return output.values.maxOrNull()!! - output.values.minOrNull()!!
    }
}