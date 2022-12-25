package year2021.day9

import model.DaySolver

class Day9Solver : DaySolver<List<String>, Int> {

    private fun parseData(data: List<String>): List<List<Int>> {
        return data.map {
            it.split("").filter { it.isNotBlank() }.map { it.toInt() }
        }
    }

    override fun part1(data: List<String>): Int {
        return count(parseData(data))
    }

    private fun allPoints(data: List<List<Int>>, point: Pair<Int,Int>): List<Pair<Int,Int>> {
        val points = listOf(
            Pair(-1,0),
            Pair(1,0),
            Pair(0,1),
            Pair(0,-1)
        )
        return  points.map { (x, y) -> Pair(point.first + x, point.second + y) }.filter { (x, y) ->
            x >= 0 && x < data.size && y >= 0 && y < data.first().size
        }
    }

    private fun count(data: List<List<Int>>): Int {
        var result = 0
        for (i in data.indices) {
            for (j in data.get(i).indices) {
                val value = data[i][j]
                if (allPoints(data, Pair(i, j)).all { (x,y) -> value < data[x][y] }){
                    result += (value + 1)
                }
            }
        }
        return result
    }

    private fun count1(data: List<List<Int>>): Int {
        val result = mutableListOf<Int>()
        var points = emptyList<Pair<Int,Int>>()
        for (i in data.indices) {
            for (j in data.get(i).indices) {
                val value = data[i][j]
                if (value == 9 || points.contains(Pair(i,j))){
                    continue
                }
                val newPoints = basinSize(data, Pair(i,j))
                points = points.plus(newPoints)
                result.add(newPoints.size)
            }
        }
        result.sort()
        result.reverse()
        return result.take(3).fold(1){x, y -> x * y}
    }

    private fun basinSize(data: List<List<Int>>, point: Pair<Int, Int>, points: List<Pair<Int,Int>> = emptyList()): List<Pair<Int, Int>> {
        if (points.contains(point)){
            return emptyList()
        }
        val coOrds = allPoints(data,point)
        var result = points.plus(point)
        for (coOrd in coOrds){
            if (result.contains(coOrd) || data[coOrd.first][coOrd.second] == 9){
                continue
            }
            val temp = basinSize(data, coOrd, result)
            result = result.plus(temp.filter { x -> !result.contains(x) })
        }
        return result
    }

    override fun part2(data: List<String>): Int {
        return count1(parseData(data))
    }
}