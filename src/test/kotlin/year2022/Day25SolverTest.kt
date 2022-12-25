package year2022

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day25SolverTest {
    private val daySolver = Day25Solver()
    private val dir = "year2022/day25"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe "2=-1=0"
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe "122-12==0-01=00-0=02"
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe ""
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe ""
    }
}