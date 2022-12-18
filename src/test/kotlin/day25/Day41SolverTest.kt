package day25

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day41SolverTest {
    private val daySolver = Day41Solver()
    private val dir = "day41"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 1651
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 1857
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 1707
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 2536
    }
}