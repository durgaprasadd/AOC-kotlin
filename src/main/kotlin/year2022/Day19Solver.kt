package year2022

import model.DaySolver

class Day19Solver : DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<List<Int>> {
        return data.map { line ->
            line.replace(":", " ").split(" ").mapNotNull { word ->
                word.trim().toIntOrNull()
            }
        }
    }

    override fun part1(data: List<String>): Int {
        val costs = parse(data)
        var result = 0
        for (cost in costs){
            result += cost[0] * findMax(
                listOf(
                    listOf(cost[1], 0, 0, 0),
                    listOf(cost[2], 0, 0, 0),
                    listOf(cost[3], cost[4], 0, 0),
                    listOf(cost[5],0, cost[6], 0)
                ),
                listOf(1,0,0,0),
                24
            )
        }
        return result
    }

    override fun part2(data: List<String>): Int {
        val costs = parse(data)
        var result = 1
        for (cost in costs.take(3)){
            result *= findMax(
                listOf(
                    listOf(cost[1], 0, 0, 0),
                    listOf(cost[2], 0, 0, 0),
                    listOf(cost[3], cost[4], 0, 0),
                    listOf(cost[5],0, cost[6], 0)
                ),
                listOf(1,0,0,0),
                32
            )
        }
        return result
    }

    private fun findMax(cost:List<List<Int>>, robots:List<Int>, maxTime:Int): Int {
        var queue = mutableListOf(0 to Triple(robots, listOf(0,0,0,0), listOf(0,0,0,0)))
        var result = 0
        var depth = 0
        while (queue.isNotEmpty()){
            val (minutes, triple) = queue.removeFirst()
            val (robos, oldInventory, mined) = triple
            if (minutes > depth && queue.size > 10000){
                queue.sortByDescending {
                    val m = it.second.third
                    m[0] + m[1]*10 + m[2]*100 + m[3]*1000
                }
                queue = queue.take(10000).toMutableList()
                depth = minutes
            }

            if (minutes == maxTime){
                result = maxOf(result, mined[3])
                continue
            }
            val newInventory = (0..3).map { oldInventory[it] + robos[it] }
            val newMined = (0..3).map { mined[it] + robos[it] }
            queue.add(minutes+1 to Triple(robos, newInventory, newMined))
            for (i in 0..3){
                val c = cost[i]
                if ((0..3).all { oldInventory[it] >= c[it]}){
                    val newRobots = robos.toMutableList()
                    newRobots[i] = newRobots[i]+1
                    val newInventory2 = (0..3).map { newInventory[it] - c[it] }
                    queue.add(minutes+1 to Triple(newRobots, newInventory2, newMined))
                }
            }
        }
        return result
    }

}