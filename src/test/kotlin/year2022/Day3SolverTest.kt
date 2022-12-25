package year2022

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day3SolverTest {
    private val daySolver = Day3Solver()
    private val dir = "year2022/day3"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 24000
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original").map { it.trim() }
        daySolver.part1(data) shouldBe 71023
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 70
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 206289
    }

    @Test
    fun `test for decrypt`() {
        val data = readDataAsList(dir, "random")
        val combs = mutableMapOf(
            "DD" to "M", "DCD" to "CM", "DCCCC" to "CM", "CCCC" to "CD",
            "LL" to "C", "LXL" to "XC", "LXXXX" to "XC", "XXXX" to "XL",
            "VV" to "X", "VIV" to "IX", "VIIII" to "IX", "IIII" to "IV"
        )

        val minimal = mutableMapOf(
            "DD" to 1, "DCD" to 1, "DCCCC" to 3, "CCCC" to 2,
            "LL" to 1, "LXL" to 1, "LXXXX" to 3, "XXXX" to 2,
            "VV" to 1, "VIV" to 1, "VIIII" to 3, "IIII" to 2
        )

        var result = 0
        for (line in data){
            var curr = line
            for (comb in combs.keys){
                if (curr.contains(comb)){
                    curr = curr.replaceFirst(comb, combs[comb]!!)
                    result += minimal[comb]!!
                }
            }
        }
        println(result)
    }
}