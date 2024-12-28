package year2024

import model.DaySolver

class Day9Solver : DaySolver<String, Long> {

    override fun part1(data: String): Long {
        var diskSize = 0
        for (i in data){
            diskSize += i-'0'
        }
        val disk = IntArray(diskSize) { -1 }
        var j = 0
        var id = 0
        var d = 0
        while(j < data.length) {
            repeat(data[j]-'0') {
                disk[d] = id
                d++
            }
            id++
            j++
            if (j < data.length)
            d += data[j]-'0'
            j ++
        }
        var start = 0
        var end = disk.size-1
        while(start < end) {
            if (disk[start] != -1) {
                start++
                continue
            }
            if (disk[end] == -1) {
                end--
                continue
            }
            disk[start] = disk[end]
            disk[end] = -1
            start++
            end--
        }
        var result = 0L
        for (i in disk.indices) {
            if(disk[i] == -1) break
            result += (disk[i] * i)
        }
        return result
    }

    override fun part2(data: String): Long {
        var diskSize = 0
        for (i in data){
            diskSize += i-'0'
        }
        val disk = IntArray(diskSize) { -1 }
        var j = 0
        var id = 0
        var d = 0
        while(j < data.length) {
            repeat(data[j]-'0') {
                disk[d] = id
                d++
            }
            id++
            j++
            if (j < data.length)
                d += data[j]-'0'
            j ++
        }
        id--
        while (id >= 0) {
            moveDisk(disk, id)
            id--
        }
        var result = 0L
        for (i in disk.indices) {
            if(disk[i] == -1) continue
            result += (disk[i] * i)
        }
        return result
    }

    private fun moveDisk(disk: IntArray, id: Int) {
        var count = 0

        for (i in disk){
            if (i == id){
                count++
            }
        }
        var curr = 0
        var index = -1
        for(i in disk.indices) {
            if (disk[i] == id) {
                return
            }
            if (disk[i] == -1) {
                curr++
                if (curr == count) {
                    index = i
                    break
                }
            }else {
                curr = 0
            }
        }
        if (index == -1) return
        for (i in disk.indices){
            if (disk[i] == id){
                disk[i] = -1
            }
        }
        repeat(count){
            disk[index] = id
            index--
        }
    }

}