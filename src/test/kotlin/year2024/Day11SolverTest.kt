package year2024

import helpers.readData
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day11SolverTest {
    private val daySolver = Day11Solver()
    private val dir = "year2024/day11"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readData(dir, "sample")
        daySolver.part1(data) shouldBe 55312L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readData(dir, "original")
        daySolver.part1(data) shouldBe 189547L
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readData(dir, "sample")
        daySolver.part2(data) shouldBe 65601038650482L
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readData(dir, "original")
        daySolver.part2(data) shouldBe 224577979481346L
    }
}