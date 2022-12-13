package day25

import helpers.readData
import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day31SolverTest {
    private val daySolver = Day31Solver()
    private val dir = "day31"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readData(dir, "sample")
        daySolver.part1(data) shouldBe 2
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readData(dir, "original")
        daySolver.part1(data) shouldBe 500
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readData(dir, "sample")
        daySolver.part2(data) shouldBe 4
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readData(dir, "original")
        daySolver.part2(data) shouldBe 815
    }
}