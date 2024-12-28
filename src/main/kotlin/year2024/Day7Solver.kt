package year2024

import model.DaySolver

class Day7Solver : DaySolver<List<String>, Long> {

    override fun part1(data: List<String>): Long {
        var result = 0L
        for (line in data) {
            val res = line.split(":")[0].toLong()
            val nums = line.split(":")[1].trim().split(" ").map { it.toLong() }
            if (valid(nums, res, nums[0], 1)) result += res
        }
        return result
    }

    private fun valid(nums: List<Long>, res: Long, curr: Long, i: Int): Boolean {
        if (i == nums.size) {
            return curr == res
        }
        return valid(nums, res, curr + nums[i], i + 1) || valid(nums, res, curr * nums[i], i + 1)
    }

    private fun valid2(nums: List<Long>, res: Long, curr: Long, i: Int): Boolean {
        if (i == nums.size) {
            return curr == res
        }
        return valid2(nums, res, curr + nums[i], i + 1) || valid2(nums, res, curr * nums[i], i + 1) || valid2(
            nums, res, (curr.toString() + nums[i].toString()).toLong(), i + 1
        )
    }

    override fun part2(data: List<String>): Long {
        var result = 0L
        for (line in data) {
            val res = line.split(":")[0].toLong()
            val nums = line.split(":")[1].trim().split(" ").map { it.toLong() }
            if (valid2(nums, res, nums[0], 1)) result += res
        }
        return result
    }

}