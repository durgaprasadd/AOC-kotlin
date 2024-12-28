package year2024

import helpers.readData
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day9SolverTest {
    private val daySolver = Day9Solver()
    private val dir = "year2024/day9"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readData(dir, "sample")
        daySolver.part1(data) shouldBe 1928L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readData(dir, "original")
        daySolver.part1(data) shouldBe 6283404590840L
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readData(dir, "sample")
        daySolver.part2(data) shouldBe 2858L
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readData(dir, "original")
        daySolver.part2(data) shouldBe 20928985450275L
    }
}