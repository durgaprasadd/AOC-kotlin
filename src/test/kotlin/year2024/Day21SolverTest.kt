package year2024

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day21SolverTest {
    private val daySolver = Day21Solver()
    private val dir = "year2024/day21"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 126384L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 215374L //26400L
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 154115708116294L
    }

    @Test
    fun `should return correct result for original data for part2`() {

        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 260586897262600L //20707955558L too low

    }
}