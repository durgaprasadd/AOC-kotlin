package year2021.day23

import model.DaySolver
import kotlin.math.abs


class Day23Solver : DaySolver<List<String>, Long> {
    var count = 1000000

    companion object {
        val cost = mapOf(
            "A" to 1,
            "B" to 10,
            "C" to 100,
            "D" to 1000,
        )
        val notValid = listOf(3, 5, 7, 9)
        val notValidCorridor = listOf(2, 4, 6, 8)
    }

    private fun parseData(data: List<String>): List<List<String>> {
        val result = data.map {
            it.trim().split("").map { it.trim() }.filter { it.isNotBlank() }
        }.filter { it.isNotEmpty() }
        val length = result.first().size
        return result.map {
            if (it.size < length) listOf("#", "#") + it + listOf("#", "#") else it
        }
    }

    override fun part1(data: List<String>): Long {
        val parsedData = parseData(data)
        parsedData.forEach { println(it) }
        val pos = mutableMapOf<Pair<List<List<Pair<Int, Int>>>, Int>, Pair<Int, Boolean>>()
        val result = move(parsedData, pos)
        println(result)
        return result.first.toLong()
    }

    private fun findPositions(data: List<List<String>>): List<List<Pair<Int, Int>>> {
        return listOf("A", "B", "C", "D").map { x ->
            val temp = mutableListOf<Pair<Int, Int>>()
            data.forEachIndexed { index, list ->
                list.forEachIndexed { index1, s ->
                    if (s == x) {
                        temp.add(Pair(index, index1))
                    }
                }
            }
            temp.toList().sortedBy {
                it.first
            }
        }
    }

    private fun move(
        data: List<List<String>>,
        pos: MutableMap<Pair<List<List<Pair<Int, Int>>>, Int>, Pair<Int, Boolean>>,
        travelCost: Int = 0
    ): Pair<Int, Boolean> {
        var temp = Pair(0, false)
//        count++
//        if (data[1][11] == "D" && data[1][1] == "D" && data[1][2] == "C" && data[2][3] == "A" && data[3][3] == "A" && data[2][5] == "B" && data[3][5] == "B") {
//            println()
//            data.forEach { println(it) }
//            println()
//        }
//        if (count > 8){
//            throw Exception("done")
//        }
        if (isDone(data)) {
            if (count > travelCost) {
                count = travelCost
                println(count)
            }
//            println("it came here")
//                println(travelCost)
            return Pair(travelCost, true)
        }
        val newPos = findPositions(data)
        if (pos.contains(Pair(newPos, travelCost))) {
            return pos[Pair(newPos, travelCost)]!!
        }
        for (i in 1..3) {
            for (j in 1..11) {
                val value = data[i][j]
                if (value in listOf("#", ".") || isAlreadyDone(data, i, j)) {
                    continue
                }
                when {
                    hallway(i) -> {
                        val moves = allPossibleMovesHallway(data, i, j)
                        if (moves.isNotEmpty()) {
                            val result = moves.map { pair ->
                                val cost = findCost(value, i, j, pair.first, pair.second)
                                move(updateData(data, i, j, pair.first, pair.second), pos, travelCost + cost)
                            }.plus(temp).filter {
                                it.second
                            }
                            if (result.isNotEmpty()) {
                                temp = result.minByOrNull { it.first }!!
                            }
                        }

                    }

                    sr1(i) -> {
                        val moves = allPossibleMovesSr1(data, i, j)
                        if (moves.isNotEmpty()) {
                            val result = moves.map { pair ->
                                val cost = findCost(value, i, j, pair.first, pair.second)
                                move(updateData(data, i, j, pair.first, pair.second), pos, travelCost + cost)
                            }.plus(temp).filter {
                                it.second
                            }
                            if (result.isNotEmpty()) {
                                temp = result.minByOrNull { it.first }!!
                            }
                        }
                    }

                    sr2(i) -> {
                        val moves = allPossibleMovesSr2(data, i, j)
                        if (moves.isNotEmpty()) {
                            val result = moves.map { pair ->
                                val cost = findCost(value, i, j, pair.first, pair.second)
                                move(updateData(data, i, j, pair.first, pair.second), pos, travelCost + cost)
                            }.plus(temp).filter {
                                it.second
                            }
                            if (result.isNotEmpty()) {
                                temp = result.minByOrNull { it.first }!!
                            }
                        }
                    }

                    else -> {
                        throw Exception("something")
                    }
                }
            }
        }
        pos[Pair(newPos, travelCost)] = temp
        return temp
    }

    private fun findCost(value: String, i: Int, j: Int, k: Int, l: Int): Int {
        return (abs(i - k) + abs(j - l)) * cost[value]!!
    }

    private fun isAlreadyDone(data: List<List<String>>, i: Int, j: Int): Boolean {
        val value = data[i][j]
        return when (value) {
            "A" -> {
                (i == 3 && j == 3) || (i == 2 && j == 3 && data[3][3] == "A")
            }
            "B" -> {
                (i == 3 && j == 5) || (i == 2 && j == 5 && data[3][5] == "B")
            }
            "C" -> {
                (i == 3 && j == 7) || (i == 2 && j == 7 && data[3][7] == "C")
            }
            "D" -> {
                (i == 3 && j == 9) || (i == 2 && j == 9 && data[3][9] == "D")
            }
            else -> {
                throw Exception("something")
            }
        }
    }

    private fun isAlreadyDone1(data: List<List<String>>, i: Int, j: Int): Boolean {
        val value = data[i][j]
        return when (value) {
            "A" -> {
                j == 3 && i > 1 && (5 downTo i).all { x -> data[x][j] == "A" }
            }
            "B" -> {
                j == 5 && i > 1 && (5 downTo i).all { x -> data[x][j] == "B" }
            }
            "C" -> {
                j == 7 && i > 1 && (5 downTo i).all { x -> data[x][j] == "C" }
            }
            "D" -> {
                j == 9 && i > 1 && (5 downTo i).all { x -> data[x][j] == "D" }
            }
            else -> {
                throw Exception("something")
            }
        }
    }

    private fun allPossibleMovesHallway(data: List<List<String>>, i: Int, j: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val value = data[i][j]
        if (!isValidMove(data, value) || !canMove(data, value, j)) {
            return result
        }

        return when (value) {
            "A" -> {
                if (data[3][3] == ".") listOf(3 to 3) else listOf(2 to 3)
            }
            "B" -> {
                if (data[3][5] == ".") listOf(3 to 5) else listOf(2 to 5)
            }
            "C" -> {
                if (data[3][7] == ".") listOf(3 to 7) else listOf(2 to 7)
            }
            "D" -> {
                if (data[3][9] == ".") listOf(3 to 9) else listOf(2 to 9)
            }
            else -> {
                throw Exception("something")
            }
        }
    }

    private fun allPossibleMovesHallway1(data: List<List<String>>, i: Int, j: Int): List<Pair<Int, Int>> {
        val value = data[i][j]
        if (!isValidMove1(data, value) || !canMove(data, value, j)) {
            return emptyList()
        }
        return when (value) {
            "A" -> {
                listOf((5 downTo 2).first { x ->
                    data[x][3] == "."
                } to 3)
            }
            "B" -> {
                listOf((5 downTo 2).first { x ->
                    data[x][5] == "."
                } to 3)
            }
            "C" -> {
                listOf((5 downTo 2).first { x ->
                    data[x][7] == "."
                } to 3)
            }
            "D" -> {
                listOf((5 downTo 2).first { x ->
                    data[x][9] == "."
                } to 3)
            }
            else -> {
                throw Exception("something")
            }
        }
    }

    private fun isValidMove(data: List<List<String>>, value: String): Boolean {
        return when (value) {
            "A" -> {
                data[3][3] in listOf(".", "A") && data[2][3] == "."
            }
            "B" -> {
                data[3][5] in listOf(".", "B") && data[2][5] == "."
            }
            "C" -> {
                data[3][7] in listOf(".", "C") && data[2][7] == "."
            }
            "D" -> {
                data[3][9] in listOf(".", "D") && data[2][9] == "."
            }
            else -> {
                throw Exception("something")
            }
        }
    }

    private fun isValidMove1(data: List<List<String>>, value: String): Boolean {
        return when (value) {
            "A" -> {
                (5 downTo 2).all { x -> data[x][3] in listOf(".", "A") }
            }
            "B" -> {
                (5 downTo 2).all { x -> data[x][5] in listOf(".", "B") }
            }
            "C" -> {
                (5 downTo 2).all { x -> data[x][7] in listOf(".", "C") }
            }
            "D" -> {
                (5 downTo 2).all { x -> data[x][9] in listOf(".", "D") }
            }
            else -> {
                throw Exception("something")
            }
        }
    }

    private fun canMove(data: List<List<String>>, value: String, j: Int): Boolean {
        return when (value) {
            "A" -> {
                (if (j < 3) (j + 1)..3 else 3 until j).all { x ->
                    data[1][x] == "."
                }
            }
            "B" -> {
                (if (j < 5) (j + 1)..5 else 5 until j).all { x ->
                    data[1][x] == "."
                }
            }
            "C" -> {
                (if (j < 7) (j + 1)..7 else 7 until j).all { x ->
                    data[1][x] == "."
                }
            }
            "D" -> {
                (if (j < 9) (j + 1)..9 else 9 until j).all { x ->
                    data[1][x] == "."
                }
            }
            else -> {
                throw Exception("something")
            }
        }
    }

    private fun allPossibleMovesSr2(data: List<List<String>>, i: Int, j: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        if (data[i - 1][j] != ".") {
            return result
        }
        var start = j - 1
        while (start > 0) {
            if (start in notValid) {
                start--
                continue
            }
            if (data[i - 2][start] == ".") {
                result.add(i - 2 to start)
            } else {
                break
            }
            start--
        }

        start = j + 1
        while (start <= 12) {
            if (start in notValid) {
                start++
                continue
            }
            if (data[i - 2][start] == ".") {
                result.add(i - 2 to start)
            } else {
                break
            }
            start++
        }
        return result
    }

    private fun allPossibleMovesSrPart2(data: List<List<String>>, i: Int, j: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        if ((i - 1 downTo 2).any { x -> data[x][j] != "." }) {
            return result
        }
        var start = j - 1
        while (start > 0) {
            if (start in notValid) {
                start--
                continue
            }
            if (data[1][start] == ".") {
                result.add(1 to start)
            } else {
                break
            }
            start--
        }

        start = j + 1
        while (start <= 12) {
            if (start in notValid) {
                start++
                continue
            }
            if (data[1][start] == ".") {
                result.add(1 to start)
            } else {
                break
            }
            start++
        }
        return result
    }

    private fun updateData(data: List<List<String>>, i: Int, j: Int, k: Int, l: Int): List<List<String>> {
        val temp = data[i][j]
        return data.mapIndexed { index, list ->
            list.mapIndexed { index1, s ->
                if (index == i && index1 == j) {
                    "."
                } else if (index == k && index1 == l) {
                    temp
                } else s
            }
        }
    }

    private fun allPossibleMovesSr1(data: List<List<String>>, i: Int, j: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        var start = j - 1
        while (start > 0) {
            if (start in notValid) {
                start--
                continue
            }
            if (data[i - 1][start] == ".") {
                result.add(i - 1 to start)
            } else {
                break
            }
            start--
        }

        start = j + 1
        while (start <= 12) {
            if (start in notValid) {
                start++
                continue
            }
            if (data[i - 1][start] == ".") {
                result.add(i - 1 to start)
            } else {
                break
            }
            start++
        }
        return result
    }

    //In side room1
    private fun sr1(i: Int): Boolean {
        return i == 2
    }

    private fun sr2(i: Int): Boolean {
        return i == 3
    }

    private fun hallway(i: Int): Boolean {
        return i == 1
    }

    private fun isDone(data: List<List<String>>): Boolean {
        val positions = mapOf(
            Pair(2, 3) to "A",
            Pair(2, 5) to "B",
            Pair(2, 7) to "C",
            Pair(2, 9) to "D",
            Pair(3, 3) to "A",
            Pair(3, 5) to "B",
            Pair(3, 7) to "C",
            Pair(3, 9) to "D",
        )
        return positions.all { (key, value) ->
            data[key.first][key.second] == value
        }
    }

    private fun isDone1(data: List<List<String>>): Boolean {
        val positions = mapOf(
            Pair(2, 3) to "A",
            Pair(2, 5) to "B",
            Pair(2, 7) to "C",
            Pair(2, 9) to "D",
            Pair(3, 3) to "A",
            Pair(3, 5) to "B",
            Pair(3, 7) to "C",
            Pair(3, 9) to "D",
            Pair(4, 3) to "A",
            Pair(4, 5) to "B",
            Pair(4, 7) to "C",
            Pair(4, 9) to "D",
            Pair(5, 3) to "A",
            Pair(5, 5) to "B",
            Pair(5, 7) to "C",
            Pair(5, 9) to "D",
        )
        return positions.all { (key, value) ->
            data[key.first][key.second] == value
        }
    }

    private fun move1(
        data: List<List<String>>,
        pos: MutableMap<Pair<List<List<Pair<Int, Int>>>, Int>, Pair<Int, Boolean>>,
        travelCost: Int = 0
    ): Pair<Int, Boolean> {
        var temp = Pair(0, false)
        println(count++)
//        if (data[1][11] == "D" && data[1][1] == "D" && data[1][2] == "C" && data[2][3] == "A" && data[3][3] == "A" && data[2][5] == "B" && data[3][5] == "B") {
//            println()
//            data.forEach { println(it) }
//            println()
//        }
//        if (count > 8){
//            throw Exception("done")
//        }
        if (isDone1(data)) {
            if (count > travelCost) {
                count = travelCost
                println(count)
            }
//            println("it came here")
//                println(travelCost)
            return Pair(travelCost, true)
        }
        val newPos = findPositions(data)
        if (pos.contains(Pair(newPos, travelCost))) {
            return pos[Pair(newPos, travelCost)]!!
        }
        for (i in 1..5) {
            for (j in 1..11) {
                val value = data[i][j]
                if (value in listOf("#", ".") || isAlreadyDone1(data, i, j)) {
                    continue
                }
                when (i) {
                    1 -> {
                        val moves = allPossibleMovesHallway1(data, i, j)
                        if (moves.isNotEmpty()) {
                            val result = moves.map { pair ->
                                val cost = findCost(value, i, j, pair.first, pair.second)
                                move1(updateData(data, i, j, pair.first, pair.second), pos, travelCost + cost)
                            }.plus(temp).filter {
                                it.second
                            }
                            if (result.isNotEmpty()) {
                                temp = result.minByOrNull { it.first }!!
                            }
                        }

                    }

                    2, 3, 4, 5 -> {
                        val moves = allPossibleMovesSrPart2(data, i, j)
                        if (moves.isNotEmpty()) {
                            val result = moves.map { pair ->
                                val cost = findCost(value, i, j, pair.first, pair.second)
                                move1(updateData(data, i, j, pair.first, pair.second), pos, travelCost + cost)
                            }.plus(temp).filter {
                                it.second
                            }
                            if (result.isNotEmpty()) {
                                temp = result.minByOrNull { it.first }!!
                            }
                        }
                    }

                    else -> {
                        throw Exception("something")
                    }
                }
            }
        }
        pos[Pair(newPos, travelCost)] = temp
        return temp
    }

    override fun part2(data: List<String>): Long {
        val parsedData = parseData1(data)
        val rooms = traverse(parsedData)
        val corridor = (1..11).map { "" }
        println(newMove(rooms, corridor))
        println(parsedData)
        println(traverse(parsedData))
        return 0
    }

    private fun newDone(rooms: List<List<String>>): Boolean {
        return rooms[0].all { it == "A" } &&
                rooms[1].all { it == "B" } &&
                rooms[2].all { it == "C" } &&
                rooms[3].all { it == "D" }
    }

    private fun newMove(rooms: List<List<String>>, corridor: List<String>, travel:Pair<Int, Boolean> = 0 to false, visited: MutableMap<Triple<List<List<String>>, List<String>, Pair<Int,Boolean>>, Pair<Int,Boolean>> = mutableMapOf()): Pair<Int, Boolean> {
        if(travel.first == 3000 && corridor[10] == "D"){
            println("it coming !!!!!!!")
        }
//        if (visited.getOrDefault(Triple(rooms,corridor,travel),null) != null){
//            return visited.getOrDefault(Triple(rooms,corridor,travel),null)!!
//        }
        var newCost = travel
        if (newDone(rooms)){
            println("it came here")
            return Pair(travel.first, true)
        }
//        if (count == 1000258){
//            println(rooms)
//            println(corridor)
//        }
//        if(corridor[0] == "A" && corridor[1] == "A" && corridor[3] == "D" && corridor[9] == "B" && corridor[10] == "D") {
//            println(rooms)
//            println(corridor)
//            println(count)
//            println()
//        }

        if(rooms.sumOf { room -> room.count { it.isNotBlank() }} + corridor.count { it.isNotBlank() } < 16 ) {
            println(rooms)
            println(corridor)
            println()
        }
        val newCorridor = corridor.toMutableList()
        var isNotDone = true
        val newRooms = rooms.map { it.toMutableList() }
        while (newCorridor.any { it.isNotBlank() } && isNotDone) {
            isNotDone = false
            for (i in newCorridor.indices) {
                val value = newCorridor[i]
                if (value.isBlank()) {
                    continue
                }
                when (value) {
                    "A" -> {
                        val range = if (i > 2) (i - 1 downTo 2) else (i + 1 until 2)
                        val canAMove = range.all { newCorridor[it].isBlank() }
                        if (canAMove) {
                            val isValidAMove = newRooms[0].all { it.isBlank() || it == "A" }
                            if (isValidAMove) {
                                val pos = newRooms[0].lastIndexOf("")
                                newCost = newCost.first + ((abs(i - 2) + pos + 1)* cost["A"]!!) to newCost.second
                                newRooms[0][pos] = "A"
                                newCorridor[i] = ""
                                isNotDone = true
                            }
                        }
                    }
                    "B" -> {
                        val range = if (i > 4) (i - 1 downTo 4) else (i + 1 until 4)
                        val canAMove = range.all { newCorridor[it].isBlank() }
                        if (canAMove) {
                            val isValidAMove = newRooms[1].all { it.isBlank() || it == "B" }
                            if (isValidAMove) {
                                val pos = newRooms[1].lastIndexOf("")
                                newCost = newCost.first + ((abs(i - 4) + pos + 1)* cost["B"]!!) to newCost.second
                                newRooms[1][pos] = "B"
                                newCorridor[i] = ""
                                isNotDone = true
                            }
                        }
                    }
                    "C" -> {
                        val range = if (i > 6) (i - 1 downTo 6) else (i + 1 until 6)
                        val canAMove = range.all { newCorridor[it].isBlank() }
                        if (canAMove) {
                            val isValidAMove = newRooms[2].all { it.isBlank() || it == "C" }
                            if (isValidAMove) {
                                val pos = newRooms[2].lastIndexOf("")
                                newCost = newCost.first + ((abs(i - 6) + pos + 1)* cost["C"]!!) to newCost.second
                                newRooms[2][pos] = "C"
                                newCorridor[i] = ""
                                isNotDone = true
                            }
                        }
                    }
                    "D" -> {
                        val range = if (i > 8) (i - 1 downTo 8) else (i + 1 until 8)
                        val canAMove = range.all { newCorridor[it].isBlank() }
                        if (canAMove) {
                            val isValidAMove = newRooms[3].all { it.isBlank() || it == "D" }
                            if (isValidAMove) {
                                val pos = newRooms[3].lastIndexOf("")
                                newCost = newCost.first + ((abs(i - 8) + pos + 1)* cost["D"]!!) to newCost.second
                                newRooms[3][pos] = "D"
                                newCorridor[i] = ""
                                isNotDone = true
                            }
                        }
                    }
                    else -> {
                        throw Exception("something")
                    }
                }
            }
        }

        if (newDone(newRooms)){
            if (count > newCost.first){
                count = newCost.first
                println(count)
            }
            return newCost.first to true
        }
        val tempCost1 = newCost
        for (i in newRooms.indices) {
            if (travel.first == 0) {
                println("it coming here $i")
            }
            val room = newRooms[i]
            if (room.all { it.isBlank() } || isRoomDone(room, i)) {
                continue
            }
            val index = room.indexOfFirst {
                it.isNotBlank()
            }
            val value = room[index]
            val range1 = (i + 1) * 2 downTo 0
            val leftRange =
                if (range1.all { newCorridor[it].isBlank() }) range1 else (i + 1) * 2 downTo range1.first { newCorridor[it].isNotBlank() } + 1
            val range2 = (i + 1) * 2 until newCorridor.size
            val rightRange =
                if (range2.all { newCorridor[it].isBlank() }) range2 else (i + 1) * 2 until range2.first { newCorridor[it].isNotBlank() }
            val possibleMoves = (leftRange + rightRange).filter { !notValidCorridor.contains(it) }
            val result = possibleMoves.map {
                val tempCost = tempCost1.first + ((abs(it - ((i+1)*2)) + index + 1)* cost[value]!!) to tempCost1.second
                newMove(changeInRoom(newRooms, i, index), changeCorridor(newCorridor, it, value), tempCost, visited)
            }.plus(newCost).filter { it.second }
            if (result.isNotEmpty()){
                newCost = result.minByOrNull {
                    it.first
                }!!
            }
        }
//        visited[Triple(rooms, corridor, travel)] = newCost
        return newCost
    }

    private fun isRoomDone(room: List<String>, i: Int): Boolean {
        return when(i){
            0 -> room.all { it.isBlank() || it == "A" }
            1 -> room.all { it.isBlank() || it == "B" }
            2 -> room.all { it.isBlank() || it == "C" }
            3 -> room.all { it.isBlank() || it == "D" }
            else -> {
                throw  Exception("is room done")
            }
        }
    }


    private fun changeCorridor(newCorridor: List<String>, i: Int, s: String): List<String> {
        val corridor = newCorridor.toMutableList()
        corridor[i] = s
        return corridor
    }

    private fun changeInRoom(newRooms: List<List<String>>, i: Int, index: Int): List<List<String>> {
        val rooms = newRooms.map { it.toMutableList() }
        rooms[i][index] = ""
        return rooms
    }

    private fun parseData1(data: List<String>): List<List<String>> {
        return data.drop(2).take(4).map { it.trim().split("").filter { it.isNotBlank() && it != "#" } }
    }

    private fun traverse(data: List<List<String>>): List<List<String>> {
        return data.mapIndexed { index, list ->
            list.mapIndexed { index1, s ->
                data[index1][index]
            }
        }
    }
}