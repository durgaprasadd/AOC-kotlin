package year2024

import helpers.readDataAsList
import io.kotlintest.shouldBe
import model.Point
import org.junit.jupiter.api.Test

internal class Day16SolverTest {
    private val daySolver = Day16Solver()
    private val dir = "year2024/day16"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 7036L //148
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 160624L //1112 //120
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 45
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 1495455L //1458540L low
    }
}