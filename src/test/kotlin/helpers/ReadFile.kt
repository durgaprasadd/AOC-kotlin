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

fun writeData(dir: String, fileName: String, data:String) {
    val fileReader = ResourceUtils.getFile("classpath:${dir}/${fileName}")
    fileReader.appendText(data + "\n\n\n\n\n\n")
}

fun <T> readDataAs(dir: String, fileName: String): T {
    return readData(dir, fileName) as T
}

fun readDataAsIntList(dir: String, fileName: String): List<Int> {
    return readDataAsList(dir, fileName).map { it.toInt() }
}

fun readDataAsLongList(dir: String, fileName: String): List<Long> {
    return readDataAsList(dir, fileName).map { it.toLong() }
}