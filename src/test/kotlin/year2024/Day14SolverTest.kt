package year2024

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day14SolverTest {
    private val daySolver = Day14Solver()
    private val dir = "year2024/day14"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 21L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 221616000L //26400L
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 875318608908L
    }

    @Test
    fun `should return correct result for original data for part2`() {

        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 100L //10000 high 7500 low
    }
}