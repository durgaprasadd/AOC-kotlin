package year2021.day21

import model.DaySolver


class Day21Solver : DaySolver<List<String>, Long> {

    companion object {
        val board = listOf(10,1,2,3,4,5,6,7,8,9)
    }

    private fun parseData(data: List<String>): List<Int> {
        return data.map {
            it.split(":")[1].trim().toInt()
        }
    }

    override fun part1(data: List<String>): Long {
        val positions = parseData(data)
        return game(positions.toMutableList())
    }

    private fun game(positions: MutableList<Int>): Long {
        val scores = mutableListOf(0L,0L)
        var dice = 1
        var count = 0
        var index = 0
        while (scores[0] < 1000 && scores[1] < 1000) {
            val sum = (dice%100) + (dice+1)%100 + (dice+2)%100
            dice = (dice+3)%100
            positions[index] = board[(positions[index] + sum) % 10]
            scores[index] = scores[index] + positions[index]
            index = (index +1)%2
            count++
        }
        return scores.minOrNull()!! * count * 3
    }
    override fun part2(data: List<String>): Long {
        val (p1, p2) = parseData(data)
        val (win1,win2) = quantumGame(p1,p2)
        return maxOf(win1,win2)
    }

    private fun quantumGame(p1: Int, p2: Int, s1:Int =0, s2:Int=0, active:Int=1, visited:MutableMap<Triple<Pair<Int,Int>,Pair<Int,Int>, Int>, Pair<Long,Long>> = mutableMapOf()): Pair<Long, Long> {
        var win1 = 0L
        var win2 = 0L
        if (visited.getOrDefault(Triple(Pair(p1,p2), Pair(s1,s2), active),null) != null){
            return visited[Triple(Pair(p1,p2),Pair(s1,s2), active)]!!
        }
        for (i in 1..3){
            for (j in 1..3){
                for (k in 1..3){
                    val sum = i + j + k
                    if(active == 1) {
                        val newP1 = board[(p1 + sum)%10]
                        val newS1 = s1 + newP1
                        if(newS1 >= 21){
                            win1 += 1
                        }
                        else {
                            val (sub_p1_wins, sub_p2_wins) = quantumGame(newP1, p2, newS1, s2, 2,visited)
                            win1 += sub_p1_wins
                            win2 += sub_p2_wins
                        }
                    }
                    else {
                        val newP2 = board[(p2 + sum)%10]
                        val newS2 = s2 + newP2
                        if(newS2 >= 21){
                            win2 += 1
                        }
                        else {
                            val (sub_p1_wins, sub_p2_wins) = quantumGame(p1, newP2, s1, newS2, 1,visited)
                            win1 += sub_p1_wins
                            win2 += sub_p2_wins
                        }
                    }
                }
            }
        }
        visited.put(Triple(Pair(p1,p2), Pair(s1,s2), active), Pair(win1,win2))
        return Pair(win1,win2)
    }
}