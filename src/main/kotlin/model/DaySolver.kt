package model

interface DaySolver<T1, T2> {
    fun part1(data: T1): T2

    fun part2(data: T1): T2
}