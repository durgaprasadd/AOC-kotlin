package year2024

import helpers.readDataAsList
import helpers.readDataAsLongList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day24SolverTest {
    private val daySolver = Day24Solver()
    private val dir = "year2024/day24"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 2024L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 51657025112326L
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe "co,de,ka,ta"
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe "ch,cz,di,gb,ht,ku,lu,tw,vf,vt,wo,xz,zk"

    }
}