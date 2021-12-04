package day2

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day2SolverTest {
    private val daySolver = Day2Solver()
    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList("day2", "sample")
        daySolver.part1(data) shouldBe 150
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList("day2", "original")
        daySolver.part1(data) shouldBe 1648020
    }
    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList("day2", "sample")
        daySolver.part2(data) shouldBe 900
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList("day2", "original")
        daySolver.part2(data) shouldBe 1759818555
    }
}