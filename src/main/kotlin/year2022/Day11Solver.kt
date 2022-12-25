package year2022

import model.DaySolver

class Day11Solver: DaySolver<List<String>, Long> {

    private fun parse(data: List<String>): MutableList<Monkey> {
        var i = 0
        val monkeys = mutableListOf<Monkey>()
        while (i < data.size){
            val curr = Monkey()
            curr.worry = data[i].split(",").map { it.trim().toLong() }.toMutableList()
            curr.op = data[i+1]
            curr.divisible = data[i+2].toInt()
            curr.first = data[i+3].toInt()
            curr.second = data[i+4].toInt()
            i += 5
            monkeys.add(curr)
        }
        return monkeys
    }
    override fun part1(data: List<String>): Long {
        val monkeys = parse(data)
        repeat(20){
            for (monkey in monkeys){
                val curr = monkey.worry
                monkey.worry = mutableListOf()
                for (w in curr){
                    var worry = w
                    if (monkey.op == "sqr"){
                        worry *= worry
                    }else if (monkey.op.startsWith("mul")){
                        worry *= monkey.op.split(" ")[1].trim().toInt()
                    }else {
                        worry += monkey.op.split(" ")[1].trim().toInt()
                    }
                    worry /= 3
                    if (worry % monkey.divisible == 0L){
                        monkeys[monkey.first].worry.add(worry)
                    }else {
                        monkeys[monkey.second].worry.add(worry)
                    }
                }
                monkey.times += curr.size
            }
        }
        val result = monkeys.map { it.times }.sorted().takeLast(2)
        return result[0] * result[1]
    }
    private fun gcd(a:Int, b:Int):Int {
        if (a < b) return gcd(b,a)
        return if (a%b == 0) b else gcd(b, a%b)
    }

    override fun part2(data: List<String>): Long {
        val monkeys = parse(data)
        var lcm = 1
        for (monkey in monkeys){
            lcm = (lcm * monkey.divisible)/gcd(lcm, monkey.divisible)
        }
        repeat(10000){
            for (monkey in monkeys){
                val curr = monkey.worry
                monkey.worry = mutableListOf()
                for (w in curr){
                    var worry = w
                    if (monkey.op == "sqr"){
                        worry *= worry
                    }else if (monkey.op.startsWith("mul")){
                        worry *= monkey.op.split(" ")[1].trim().toInt()
                    }else {
                        worry += monkey.op.split(" ")[1].trim().toInt()
                    }
                    worry %= lcm
                    if (worry % monkey.divisible == 0L){
                        monkeys[monkey.first].worry.add(worry)
                    }else {
                        monkeys[monkey.second].worry.add(worry)
                    }
                }
                monkey.times += curr.size
            }
        }
        val result = monkeys.map { it.times }.sorted().takeLast(2)
        return result[0] * result[1]
    }

}

data class Monkey(
    var worry:MutableList<Long> = mutableListOf(),
    var op:String = "",
    var divisible:Int = 1,
    var first:Int = 0,
    var second:Int = 0,
    var times:Long = 0
)