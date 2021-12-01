package day1

import helpers.readDataAsIntList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day1SolverTest {
    private val day1Solver = Day1Solver()
        @Test
        fun `should return correct result for sample data for part1`() {
            val data = readDataAsIntList("day1", "sample")
            day1Solver.part1(data) shouldBe 7
        }

        @Test
        fun `should return correct result for original data for part1`() {
            val data = readDataAsIntList("day1", "original")
            day1Solver.part1(data) shouldBe 1466
        }
        @Test
        fun `should return correct result for sample data for part2`() {
            val data = readDataAsIntList("day1", "sample")
            day1Solver.part2(data) shouldBe 5
        }

        @Test
        fun `should return correct result for original data for part2`() {
            val data = readDataAsIntList("day1", "original")
            day1Solver.part2(data) shouldBe 1491
        }
}