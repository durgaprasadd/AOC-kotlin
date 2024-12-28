package year2024

import model.DaySolver

class Day22Solver : DaySolver<List<Long>, Long> {

    override fun part1(data: List<Long>): Long {
        println(data)
        var count = 0L
        for (num in data) {
            var curr = num
            repeat(2000) {
                curr = next(curr)
            }
            println(curr)
            count += curr
        }
        return count
    }

    private fun next(curr: Long): Long {
        return step3(step2(step1(curr)))
    }

    private fun step1(secret:Long) :Long {
        return prune(mix(secret, secret*64))
    }

    private fun step2(secret:Long) :Long {
        return prune(mix(secret, secret/32))
    }

    private fun step3(secret:Long) :Long {
        return prune(mix(secret, secret*2048))
    }

    private fun mix(num: Long, secret: Long) : Long {
        return num xor secret
    }

    private fun prune(num: Long) : Long {
        return num%16777216
    }


    override fun part2(data: List<Long>): Long {
        var count = 0L
        val res = mutableMapOf<List<Long>,Long>()
        for (num in data) {
            val l = mutableListOf<Long>()
            var curr = num
            l.add(curr%10)
            repeat(2000) {
                curr = next(curr)
                l.add(curr%10)
            }
            val map = mutableMapOf<List<Long>,Long>()
            for (i in 4 until l.size){
                val sequence = listOf(
                    l[i-3] - l[i-4],
                    l[i-2] - l[i-3],
                    l[i-1] - l[i-2],
                    l[i] - l[i-1],
                )
                if (map[sequence] == null){
                    map[sequence] = l[i]
                }
            }
            for ((k,v) in map) {
                if (res[k] == null){
                    res[k] = v
                }else {
                    res[k] = res[k]!! + v
                }
            }
        }
        for ((_,v) in res){
            if (v > count) count = v
        }
        return count
    }

}





