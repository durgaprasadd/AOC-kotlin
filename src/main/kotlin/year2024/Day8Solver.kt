package year2024

import model.DaySolver

class Day8Solver : DaySolver<List<String>, Long> {

    override fun part1(data: List<String>): Long {
        var result = 0L
        val grid = Array(data.size) {
            BooleanArray(data[0].length)
        }
        val map = mutableMapOf<Char, MutableList<Pair<Int,Int>>>()
        for (i in data.indices){
            for (j in data[i].indices){
                if (data[i][j] == '.') continue
                if(map[data[i][j]] == null){
                    map[data[i][j]] = mutableListOf()
                }
                map[data[i][j]]!!.add(i to j)
            }
        }
        for((k,v) in map) {
            for (i in v.indices){
                for (j in i+1 until v.size) {
                    val (x1, y1) = v[i]
                    val (x2, y2) = v[j]
                    val prevX = x1-(x2-x1)
                    val prevY = y1-(y2-y1)
                    val nextX = x2+(x2-x1)
                    val nextY = y2+(y2-y1)
                    if(prevX in data.indices && prevY in data[0].indices){
                        grid[prevX][prevY] = true
                    }
                    if(nextX in data.indices && nextY in data[0].indices){
                        grid[nextX][nextY] = true
                    }
                }
            }
        }

        for (row in grid){
            for (cell in row){
                if (cell) result++
            }
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
        val grid = Array(data.size) {
            BooleanArray(data[0].length)
        }
        val map = mutableMapOf<Char, MutableList<Pair<Int,Int>>>()
        for (i in data.indices){
            for (j in data[i].indices){
                if (data[i][j] == '.') continue
                if(map[data[i][j]] == null){
                    map[data[i][j]] = mutableListOf()
                }
                map[data[i][j]]!!.add(i to j)
            }
        }
        for((k,v) in map) {
            for (i in v.indices){
                for (j in i+1 until v.size) {
                    val (x1, y1) = v[i]
                    val (x2, y2) = v[j]
                    grid[x1][y1] = true
                    grid[x2][y2] = true
                    var prevX = x1-(x2-x1)
                    var prevY = y1-(y2-y1)
                    var nextX = x2+(x2-x1)
                    var nextY = y2+(y2-y1)
                    while(prevX in data.indices && prevY in data[0].indices){
                        grid[prevX][prevY] = true
                        prevX -= (x2-x1)
                        prevY -= (y2-y1)
                    }
                    while(nextX in data.indices && nextY in data[0].indices){
                        grid[nextX][nextY] = true
                        nextX += (x2-x1)
                        nextY += (y2-y1)
                    }
                }
            }
        }

        for (row in grid){
            for (cell in row){
                if (cell) result++
            }
        }

        return result
    }

}