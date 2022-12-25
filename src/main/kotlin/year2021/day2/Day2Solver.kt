package year2021.day2

import model.DaySolver

data class Command (
    val direction: String,
    val distance: Int
    )

data class Position (
    private var horizontal: Int = 0,
    private var depth: Int = 0,
    private var aim:Int = 0
        ) {
    fun forward(distance: Int){
        horizontal+= distance
    }

    fun forwardWithAim(distance: Int){
        forward(distance)
        depth += aim * distance
    }

    fun up(distance: Int){
        depth -= distance
    }

    fun  down(distance: Int){
        depth += distance
    }

    fun upAim(distance: Int){
        aim -= distance
    }

    fun downAim(distance: Int){
        aim += distance
    }

    fun product(): Int {
        return horizontal * depth
    }
}

class Day2Solver : DaySolver<List<String>, Int> {
    private fun parseData(data: List<String>): List<Command> {
        return data.map {
            val x = it.split(' ')
            Command(x[0], x[1].toInt())
        }
    }
    override fun part1(data: List<String>): Int {
        val commands = parseData(data)
        val position = Position()
        commands.forEach {command ->
            when(command.direction) {
                "forward" -> position.forward(command.distance)
                "up" -> position.up(command.distance)
                "down" -> position.down(command.distance)
            }
        }
        return position.product()
    }

    override fun part2(data: List<String>): Int {
        val commands = parseData(data)
        val position = Position()
        commands.forEach {command ->
            when(command.direction) {
                "forward" -> position.forwardWithAim(command.distance)
                "up" -> position.upAim(command.distance)
                "down" -> position.downAim(command.distance)
            }
        }
        return position.product()
    }
}