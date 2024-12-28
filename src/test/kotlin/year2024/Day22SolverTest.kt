package year2024

import helpers.readDataAsList
import helpers.readDataAsLongList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day22SolverTest {
    private val daySolver = Day22Solver()
    private val dir = "year2024/day22"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsLongList(dir, "sample")
        daySolver.part1(data) shouldBe 37990510L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsLongList(dir, "original")
        daySolver.part1(data) shouldBe 17960270302L
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsLongList(dir, "sample")
        daySolver.part2(data) shouldBe 23L
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsLongList(dir, "original")
        daySolver.part2(data) shouldBe 2042L //20707955558L too low

    }
}