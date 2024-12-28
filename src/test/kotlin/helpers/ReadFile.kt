package helpers

import org.springframework.util.ResourceUtils


fun readDataAsList(dir: String, fileName: String): List<String> {
    val fileReader = ResourceUtils.getFile("classpath:${dir}/${fileName}")
    return fileReader.readLines()
}

fun readData(dir: String, fileName: String): String {
    val fileReader = ResourceUtils.getFile("classpath:${dir}/${fileName}")
    return fileReader.readText()
}

fun readDataAsIntList(dir: String, fileName: String): List<Int> {
    return readDataAsList(dir, fileName).map { it.toInt() }
}

fun readDataAsLongList(dir: String, fileName: String): List<Long> {
    return readDataAsList(dir, fileName).map { it.trim().toLong() }
}