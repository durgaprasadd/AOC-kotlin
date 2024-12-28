package model

data class Point(var x: Int, var y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun times(other: Point) = Point(
        x * other.x - y * other.y,
        x * other.y + y * other.x
    )
}