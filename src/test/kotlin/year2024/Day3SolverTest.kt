package year2024

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day3SolverTest {
    private val daySolver = Day3Solver()
    private val dir = "year2024/day3"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 15
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 12855
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 12
    }

    @Test
    fun `should return correct result for original data for part2`() {
        //99812796
        //92626942
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 13726
    }
}