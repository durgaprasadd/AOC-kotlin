package day25

import helpers.readData
import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day31SolverTest {
    private val daySolver = Day31Solver()
    private val dir = "day31"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readData(dir, "sample")
        daySolver.part1(data) shouldBe 2
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readData(dir, "original")
        daySolver.part1(data) shouldBe 500
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readData(dir, "sample")
        daySolver.part2(data) shouldBe 4
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readData(dir, "original")
        daySolver.part2(data) shouldBe 815
    }

    @Test
    fun something(){
        val a = (15499).toDouble()/94744
        for (i in 102557625 until 102557630){
            val b = resilient(i)
            println("$i $b $a ${b<a}")
        }
        println(resilient(102550000))
    }

    private fun resilient(n:Int): Double {
        var count = 1.0
        for (i in 2 until n){
            if (gcd(n,i) == 1){
                count++
            }
        }
        return count/(n-1)
    }

    private fun gcd(a:Int,b:Int): Int {
        return if (a%b == 0) b else gcd(b, a%b)
    }
}