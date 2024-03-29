package year2022

import helpers.readData
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day17SolverTest {
    private val daySolver = Day17Solver()
    private val dir = "year2022/day17"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readData(dir, "sample")
        daySolver.part1(data) shouldBe 3068L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readData(dir, "original")
        daySolver.part1(data) shouldBe 3179
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readData(dir, "sample")
        daySolver.part2(data) shouldBe 1514285714288L
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readData(dir, "original")
        daySolver.part2(data) shouldBe 1567723342929L
    }
}