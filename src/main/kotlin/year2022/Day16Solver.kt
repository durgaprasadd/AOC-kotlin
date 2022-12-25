package year2022

import model.DaySolver

class Day16Solver : DaySolver<List<String>, Int> {

    private fun parse(data: List<String>): List<Pair<String, Valve>> {
        return data.map {
            val lines = it.split(";")
            val first = lines[0].split(" ")
            val valve = first[1]
            val rate = first[4].split("=")[1].toInt()
            val second =
                if (lines[1].contains("valves")) lines[1].trim().split("valves") else lines[1].trim().split("valve")
            val lead = second[1].trim().split(", ")
            valve to Valve(valve, rate, lead)
        }
    }

    override fun part1(data: List<String>): Int {
        val input = parse(data).toMap()
        val keyRooms = input.filter { (k, v) -> v.rate > 0 || k == "AA" }.keys
        val distance = findDistance(input, keyRooms)
        return findBestFlow("AA", emptySet(), 30, keyRooms, distance, input)
    }

    private fun findDistance(input: Map<String, Valve>, keyRooms: Set<String>): MutableMap<Pair<String, String>, Int> {
        val distance = mutableMapOf<Pair<String, String>, Int>()
        for (r in input.keys) {
            if (r !in keyRooms) continue;
            var cur = mutableSetOf(r)
            var next = mutableSetOf<String>()
            var dist = 0
            distance[r to r] = 0
            while (cur.isNotEmpty()) {
                dist++
                for (pos in cur) {
                    for (newPos in input[pos]!!.lead) {
                        if ((r to newPos) !in distance) {
                            distance[r to newPos] = dist
                            next.add(newPos)
                        }
                    }
                }
                cur = next
                next = mutableSetOf()
            }
        }
        return distance
    }

    private fun findBestFlow(
        curr: String,
        visited: Set<String>,
        time: Int,
        keyRooms: Set<String>,
        distance: Map<Pair<String, String>, Int>,
        input: Map<String, Valve>
    ): Int {
        var bestFlow = 0
        val remaining = keyRooms.minus(visited).minus(curr)
        for (room in remaining) {
            val remainingTime = time - distance[curr to room]!! - 1
            if (remainingTime > 0) {
                var currFlow = input[room]!!.rate * remainingTime
                currFlow += findBestFlow(room, visited.plus(curr), remainingTime, remaining, distance, input)
                if (currFlow > bestFlow) {
                    bestFlow = currFlow
                }
            }
        }
        return bestFlow
    }

    override fun part2(data: List<String>): Int {
        val input = parse(data).toMap()
        val keyRooms = input.filter { (k, v) -> v.rate > 0 || k == "AA" }.keys
        val distance = findDistance(input, keyRooms)
        val endPoints = mutableMapOf<Set<String>, Int>()
        findAndRecord("AA", 0, 26, emptySet(), keyRooms, distance, input, endPoints)
        fillEndPoints(endPoints, keyRooms - "AA")
        var bestFlow = 0
        for (k in endPoints.keys){
            val elephant = keyRooms - "AA" - k
            val totalFlow = endPoints[k]!! + endPoints[elephant]!!
            if (totalFlow > bestFlow){
                bestFlow = totalFlow
            }
        }
        return bestFlow
    }

    private fun findAndRecord(
        curr: String,
        currFlow: Int,
        time: Int,
        seen: Set<String>,
        keyRooms: Set<String>,
        distance: Map<Pair<String, String>, Int>,
        input: Map<String, Valve>,
        endPoints: MutableMap<Set<String>, Int>,
    ): Int {
        val remaining = keyRooms - seen - curr
        val path = seen + curr - "AA"
        endPoints[path] = maxOf(endPoints.getOrDefault(path, 0), currFlow)
        var bestFlow = 0
        for (room in remaining) {
            val remainingTime = time - distance[(curr to room)]!! - 1
            if (remainingTime > 0) {
                var newFlow = input[room]!!.rate * remainingTime
                newFlow += findAndRecord(
                    room,
                    currFlow + newFlow,
                    remainingTime,
                    seen + curr,
                    keyRooms,
                    distance,
                    input,
                    endPoints
                )
                if (newFlow > bestFlow) bestFlow = newFlow
            }
        }
        return bestFlow
    }

    private fun fillEndPoints(endPoints: MutableMap<Set<String>, Int>, curr: Set<String>) :Int{
        if (curr !in endPoints){
            var bestFlow = 0
            for (e in curr){
                val sub = curr - e
                val newFlow = fillEndPoints(endPoints, sub)
                if (newFlow > bestFlow){
                    bestFlow = newFlow
                }
            }
            endPoints[curr] = bestFlow
        }
        return endPoints[curr]!!
    }


}

data class Valve(val name: String, val rate: Int, val lead: List<String>)