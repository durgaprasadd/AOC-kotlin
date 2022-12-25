package year2021.day13

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day13SolverTest {
    private val daySolver = Day13Solver()
    private val dir = "year2021/day13"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 17
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 592
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 0 // output is printing, find different way to test
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 0 // output is printing, find different way to test
    }
}