package year2021.day18

import model.DaySolver


fun String.isNumber(): Boolean {
    return this.all { x -> x.isDigit() }
}

class Day18Solver : DaySolver<List<String>, Long> {

    private fun parseData(data: List<String>): List<List<String>> {
        return data.map { it.trim().map { x -> x }.filter { c -> c != ',' }.map { x -> x.toString() } }
    }

    override fun part1(data: List<String>): Long {
        val reducedSnailFish = parseData(data).reduce { first, second ->
            reduceSnailFish(addTwoSnailFish(first, second))
        }
        return findValue(reducedSnailFish)
    }

    private fun reduceSnailFish(data: List<String>): List<String> {
        var isExplode = true
        var isSplit = true
        var temp = data
        while (isExplode || isSplit) {
            isExplode = false
            isSplit = false
            var count = 0
            var index = 0
            while (index < temp.size) {
                if (temp[index] == "[") count++
                if (temp[index] == "]") count--
                if (count > 4 && temp[index].isNumber() && temp[index + 1].isNumber()) {
                    isExplode = true
                    break
                }
                index++
            }
            if (isExplode) {
                val first = temp[index].toInt()
                val second = temp[index + 1].toInt()
                temp = temp.toMutableList()
                addAtStarting(temp, index, first)
                addAtEnding(temp, index + 1, second)
                temp = temp.subList(0, index - 1) + listOf("0") + temp.subList(index + 3, temp.size)
                continue
            }
            index = 0
            while (index < temp.size) {
                if (temp[index].isNumber() && temp[index].toInt() > 9) {
                    isExplode = true
                    val value = temp[index].toInt()
                    val first = value / 2
                    val second = value - first
                    temp =
                        temp.subList(0, index) + listOf("[", first.toString(), second.toString(), "]") + temp.subList(
                            index + 1,
                            temp.size
                        )
                    break
                }
                index++
            }
        }
        return temp
    }

    private fun addAtEnding(temp: MutableList<String>, index: Int, second: Int) {
        for (i in index + 1 until temp.size) {
            if (temp[i].isNumber()) {
                temp[i] = (temp[i].toInt() + second).toString()
                return
            }
        }
        return
    }

    private fun addAtStarting(temp: MutableList<String>, index: Int, first: Int) {
        for (i in index - 1 downTo 0) {
            if (temp[i].isNumber()) {
                temp[i] = (temp[i].toInt() + first).toString()
                return
            }
        }
        return
    }

    private fun findValue(data: List<String>): Long {
        var temp = data
        var isChanged = true
        while (isChanged) {
            isChanged = false
            var index = 0
            while (index < temp.size - 1) {
                if (temp[index].isNumber() && temp[index + 1].isNumber()) {
                    val first = temp[index].toLong() * 3
                    val second = temp[index + 1].toLong() * 2
                    temp = temp.subList(0, index - 1) + listOf((first + second).toString()) + temp.subList(
                        index + 3,
                        temp.size
                    )
                    isChanged = true
                }
                index++
            }
        }
        return temp.first().toLong()
    }

    override fun part2(data: List<String>): Long {
        val parsedData = parseData(data)
        var maxMagnitude = 0L
        for (i in parsedData.indices) {
            for (j in parsedData.indices) {
                if (i == j) {
                    continue
                }
                val result = reduceSnailFish(addTwoSnailFish(parsedData[i], parsedData[j]))
                val magnitude = findValue(result)
                if (magnitude > maxMagnitude) {
                    maxMagnitude = magnitude
                }
            }
        }
        return maxMagnitude
    }

    fun addTwoSnailFish(first: List<String>, second: List<String>): List<String> {
        return listOf("[") + first + second + listOf("]")
    }
}