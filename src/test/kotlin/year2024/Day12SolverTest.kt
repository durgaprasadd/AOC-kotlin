package year2024

import helpers.readDataAsList
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class Day12SolverTest {
    private val daySolver = Day12Solver()
    private val dir = "year2024/day12"

    @Test
    fun `should return correct result for sample data for part1`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part1(data) shouldBe 1930L
    }

    @Test
    fun `should return correct result for original data for part1`() {
        val data = readDataAsList(dir, "original")
        daySolver.part1(data) shouldBe 694L
    }

    @Test
    fun `should return correct result for sample data for part2`() {
        val data = readDataAsList(dir, "sample")
        daySolver.part2(data) shouldBe 81L
    }

    @Test
    fun `should return correct result for original data for part2`() {
        val data = readDataAsList(dir, "original")
        daySolver.part2(data) shouldBe 1497L //839438L
    }

    @Test
    fun `something`(){
        println( solve(intArrayOf(4,5 ,5 ,6 ,6 ,7 ,7 ,7 ,7 ,7 ,9 ,9), 7, 0, 11))
    }

    fun solve(arr: IntArray, target: Int, low: Int, high: Int): Int {
        println("low " + low + "high " + high)
        if (high < low || target < arr[low] || target > arr[high]) return 0
        if (low == high) return 1
        val mid = (low + high) / 2
        if (arr[mid] == target) {
            return 1 + solve(arr, target, low, mid - 1) + solve(arr, target, mid + 1, high)
        }

        if (arr[mid] < target) {
            return solve(arr, target, mid + 1, high)
        }

        return solve(arr, target, low, mid - 1)
    }



}