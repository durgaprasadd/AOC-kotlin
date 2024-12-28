package year2024

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day19SolverTest {
    private val daySolver = Day19Solver()
    private val dir = "year2024/day19"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 6L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 26810L //26400L
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 16L
    }

    @Test
    fun `should return correct result for original data for part2`() {

        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 100L //20707955558L too low
    }
}