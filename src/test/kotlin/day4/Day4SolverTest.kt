package day4

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day4SolverTest {
    private val daySolver = Day4Solver()
    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList("day4", "sample")
        daySolver.part1(data) shouldBe 4512
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList("day4", "original")
        daySolver.part1(data) shouldBe 58838
    }
    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList("day4", "sample")
        daySolver.part2(data) shouldBe 1924
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList("day4", "original")
        daySolver.part2(data) shouldBe 6256
    }
}