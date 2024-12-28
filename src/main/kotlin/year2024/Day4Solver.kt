package year2024

import model.DaySolver

class Day4Solver : DaySolver<List<String>, Int> {

    override fun part1(data: List<String>): Int {
        var count = 0
        for (i in data.indices) {
            for (j in data[i].indices) {
                if (data[i][j] == 'X') {
                    if (j + 3 < data[i].length && data[i][j + 1] == 'M' && data[i][j + 2] == 'A' && data[i][j + 3] == 'S') {
                        count++
                    }
                    if (i + 3 < data.size && data[i + 1][j] == 'M' && data[i + 2][j] == 'A' && data[i + 3][j] == 'S') {
                        count++
                    }
                    if (i + 3 < data.size && j + 3 < data[i].length && data[i + 1][j + 1] == 'M' && data[i + 2][j + 2] == 'A' && data[i + 3][j + 3] == 'S') {
                        count++
                    }
                    if (i + 3 < data.size && j - 3 >= 0 && data[i + 1][j - 1] == 'M' && data[i + 2][j - 2] == 'A' && data[i + 3][j - 3] == 'S') {
                        count++
                    }
                    if (j - 3 >= 0 && data[i][j - 1] == 'M' && data[i][j - 2] == 'A' && data[i][j - 3] == 'S') {
                        count++
                    }
                    if (i - 3 >= 0 && j - 3 >= 0 && data[i - 1][j - 1] == 'M' && data[i - 2][j - 2] == 'A' && data[i - 3][j - 3] == 'S') {
                        count++
                    }
                    if (i - 3 >= 0 && data[i - 1][j] == 'M' && data[i - 2][j] == 'A' && data[i - 3][j] == 'S') {
                        count++
                    }
                    if (i - 3 >= 0 && j + 3 < data[i].length && data[i - 1][j + 1] == 'M' && data[i - 2][j + 2] == 'A' && data[i - 3][j + 3] == 'S') {
                        count++
                    }

                }
            }
        }
        return count
    }


    override fun part2(data: List<String>): Int {
        var count = 0
        for (i in 1 until data.size - 1) {
            for (j in 1 until data[i].length - 1) {
                if (data[i][j] != 'A') {
                    continue
                }

                if (data[i - 1][j - 1] == 'M' && data[i + 1][j + 1] == 'S' && data[i - 1][j + 1] == 'M' && data[i + 1][j - 1] == 'S') {
                    count++
                }

                if (data[i - 1][j - 1] == 'M' && data[i + 1][j + 1] == 'S' && data[i - 1][j + 1] == 'S' && data[i + 1][j - 1] == 'M') {
                    count++
                }

                if (data[i - 1][j - 1] == 'S' && data[i + 1][j + 1] == 'M' && data[i - 1][j + 1] == 'M' && data[i + 1][j - 1] == 'S') {
                    count++
                }

                if (data[i - 1][j - 1] == 'S' && data[i + 1][j + 1] == 'M' && data[i - 1][j + 1] == 'S' && data[i + 1][j - 1] == 'M') {
                    count++
                }
            }
        }
        return count
    }

}