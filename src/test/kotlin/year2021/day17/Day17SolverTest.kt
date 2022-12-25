package year2021.day17

import helpers.readData
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day17SolverTest {
    private val daySolver = Day17Solver()
    private val dir = "year2021/day17"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readData(dir, "sample")
        daySolver.part1(data) shouldBe 45
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readData(dir, "original")
        daySolver.part1(data) shouldBe 4186
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readData(dir, "sample")
        daySolver.part2(data) shouldBe 112
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readData(dir, "original")
        daySolver.part2(data) shouldBe 2709
    }
}