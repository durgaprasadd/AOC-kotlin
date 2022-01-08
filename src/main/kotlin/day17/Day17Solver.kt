package day17

import model.DaySolver

class Day17Solver : DaySolver<String, Int> {

    private fun parseData(data: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val targets = data.split("target area: ")[1].split(",").map { x -> x.trim() }
            .map {
                val range = it.split("=")[1].trim().split("..")
                range[0].toInt() to range[1].toInt()
            }
        return targets[0] to targets[1]
    }

    override fun part1(data: String): Int {
        return findMaxHeightInTrajectory(parseData(data))
    }

    private fun findMaxHeightInTrajectory(target: Pair<Pair<Int, Int>, Pair<Int, Int>>): Int {
        var result = 0
        for (x in -100..100){
            for (y in -100..100){
                var flag = true
                var velocity = Pair(x,y)
                var position = Pair(0,0)
                var maxY = 0
                while (flag){
                    val (x1,y1) = velocity
                    position = Pair(position.first + x1, position.second+y1)
                    if (position.second > maxY) {
                        maxY = position.second
                    }
                    val (x3,y3) = position
                    if ((x3 >= target.first.first && x3 <= target.first.second) && (y3 >= target.second.first && y3 <= target.second.second)) {
                        if (maxY > result){
                            result = maxY
                        }
                    }
                    val x2 = if (x1 > 0) x1-1 else if(x1 < 0) x1 +1 else x1
                    velocity = Pair(x2, y1-1)
                    flag = position.second >= target.second.first
                }
            }
        }
        return result
    }

    private fun findAllPointsInTrajectory(target: Pair<Pair<Int, Int>, Pair<Int, Int>>): Int {
        var result = 0
        for (x in -1000..1000){
            for (y in target.second.first..1000){
                var flag = true
                var velocity = Pair(x,y)
                var position = Pair(0,0)
                var isReach = 0
                while (flag){
                    val (x1,y1) = velocity
                    position = Pair(position.first + x1, position.second+y1)
                    val (x3,y3) = position
                    if ((x3 >= target.first.first && x3 <= target.first.second) && (y3 >= target.second.first && y3 <= target.second.second)) {
                        isReach = 1
                    }
                    val x2 = if (x1 > 0) x1-1 else if(x1 < 0) x1 +1 else x1
                    velocity = Pair(x2, y1-1)
                    flag = position.second >= target.second.first
                }
                result += isReach
            }
        }
        return result
    }
    override fun part2(data: String): Int {
        return findAllPointsInTrajectory(parseData(data))
    }
}