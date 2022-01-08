package day23

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day23SolverTest {
    private val daySolver = Day23Solver()
    private val dir = "day23"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 739785
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 921585
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 444356092776315
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 911090395997650
    }

    @Test
    fun something(){
        val something = listOf("A","","","B")
        val index = 6 downTo 4
        println(index.last { it%2 == 0})
    }
}