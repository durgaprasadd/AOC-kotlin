package year2024

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day20SolverTest {
    private val daySolver = Day20Solver()
    private val dir = "year2024/day20"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 0L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 1399L
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 0L
    }

    @Test
    fun `should return correct result for original data for part2`() {

        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 994807L //20707955558L too low
    }
}