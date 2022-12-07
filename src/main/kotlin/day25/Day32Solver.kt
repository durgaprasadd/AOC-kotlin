package day25

import model.DaySolver
import java.util.*

class Day32Solver: DaySolver<List<String>, Long> {

    private fun parse(data: List<String>): Directory {
        val main = Directory("main")
        var i = 1;
        val d = Stack<Directory>()
        d.add(main)
        while (i < data.size){
            val curr = d.peek()
            when {
                data[i] == "$ cd .." -> {
                    d.pop()
                    i++
                }
                data[i].startsWith("$ cd") -> {
                    val name = data[i].replaceFirst("$ cd ", "")
                    val dir = Directory(name)
                    curr.directories.add(dir)
                    d.add(dir)
                    i++
                }
                data[i].startsWith("$ ls") -> {
                    i++
                    while (i < data.size && !data[i].startsWith("$")){
                        if (!data[i].startsWith("dir")) {
                            val words = data[i].split(" ")
                            val file = File(words[1], words[0].toLong())
                            curr.files.add(file)
                        }
                        i++
                    }
                }
            }
        }
        return main
    }
    override fun part1(data: List<String>): Long {
        val main = parse(data)
        main.findSize()
        return main.part1()
    }

    override fun part2(data: List<String>): Long {
        val main = parse(data)
        val s = main.findSize()
        return main.part2(30000000 - (70000000-s))
    }

}

data class Directory(
    val name:String,
    val directories: MutableSet<Directory> = mutableSetOf(),
    val files: MutableSet<File> = mutableSetOf(),
    var size: Long = 0
){
    fun findSize(): Long{
        for (file in files){
            size += file.size
        }
        for (directory in directories){
            size += directory.findSize()
        }
        return size
    }

    fun part1(): Long {
        var result = 0L
        if (size <= 100000){
            result += size
        }
        for (directory in directories){
            result+= directory.part1()
        }
        return result
    }

    fun part2(req: Long):Long {
        var result = 0L
        if(size >= req){
            result = size
        }
        for (directory in directories){
            val s = directory.part2(req)
            if (s >= req){
                result = minOf(s, result)
            }
        }
        return result
    }
}

data class File(
    val name: String,
    val size:Long
)