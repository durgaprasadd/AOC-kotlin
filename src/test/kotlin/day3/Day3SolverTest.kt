package day3

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day3SolverTest {
    private val daySolver = Day3Solver()
    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList("day3", "sample")
        daySolver.part1(data) shouldBe 198
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList("day3", "original")
        daySolver.part1(data) shouldBe 3549854
    }
    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList("day3", "sample")
        daySolver.part2(data) shouldBe 230
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList("day3", "original")
        daySolver.part2(data) shouldBe 3765399
    }
}