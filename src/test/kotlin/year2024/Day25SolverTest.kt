package year2024

import helpers.readDataAsIntList
import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test
import java.util.*

internal class Day25SolverTest {
    private val daySolver = Day25Solver()
    private val dir = "year2024/day25"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 3
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 2618
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 0
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 0

    }

    @Test
    fun something(){
        val d = readDataAsList(dir, "extra")
        val data = Array(4) {
            mutableListOf<Int>()
        }
        for (l in d){
            l.split(" ").forEachIndexed { index, s ->
                data[index].add(s.toInt())
            }
        }
        var i = 0
        val f = mutableMapOf<String, Int>()
        while(false){
            val curr = data[i%data.size].removeAt(0)
            i++
            val dir = (curr-1)/data[i%data.size].size
            val rem = dir%data[i%data.size].size
            if (dir%2 == 0) {
                data[i%data.size].add(rem-1, curr)
            }else {
                data[i%data.size].add(curr-1, curr)
            }
            val c = data.map { it.first() }.joinToString("")
            f[c] = f.getOrDefault(c,0)+1
            if (f[c]!! > 2023){
                println(c)
                println(i)
                break
            }
        }
        var res = "00000000"
        val q = PriorityQueue<List<Int>> { o1, o2 ->
            if (o1[0] == o2[0]) {
                o2[1].compareTo(o1[1])
            } else o2[0].compareTo(o1[0])
        }

        q.add(listOf(2,2))
        q.add(listOf(2,3))
        println(q.peek())
        for((k,v) in f){
            if (k > res) {
                res = k
            }
        }
        println(res)
        println(data.map { it.first() })

        //9687804679747927
        //9687100310081008
    }

    fun abs(a:Int, b:Int): Int {
        return if(a>b) a-b else b-a
    }

}