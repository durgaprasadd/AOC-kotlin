package year2024

import model.DaySolver
import model.Point
import java.util.PriorityQueue

class Day16Solver : DaySolver<List<String>, Any> {

    override fun part1(dx: List<String>): Long {
        val data = dx.map { it.toCharArray() }
        val pos = data.size - 2 to 1
        val x = listOf(0, 1, 0, -1)
        val y = listOf(1, 0, -1, 0)
        val q = PriorityQueue<List<Int>>(
            compareBy { it[3] }
        )
        q.add(listOf(pos.first, pos.second, 0, 0))
        val set = mutableSetOf<List<Int>>()
        while (q.isNotEmpty()) {
            val (i, j, d, steps) = q.poll()
            if (data[i][j] == 'E') {
                println(steps)
                return steps.toLong()
            }
            if (set.contains(listOf(i, j, d))) continue
            set.add(listOf(i, j, d))
            if (data[i + x[d]][j + y[d]] == '.' || data[i + x[d]][j + y[d]] == 'E') {
                q.add(listOf(i + x[d], j + y[d], d, steps + 1))
            }
            val r = (d + 1) % 4
            val l = (d + 3) % 4
            if (data[i + x[r]][j + y[r]] == '.' || data[i + x[r]][j + y[r]] == 'E') {
                q.add(listOf(i + x[r], j + y[r], r, steps + 1 + 1000))
            }
            if (data[i + x[l]][j + y[l]] == '.' || data[i + x[l]][j + y[l]] == 'E') {
                q.add(listOf(i + x[l], j + y[l], l, steps + 1 + 1000))
            }
        }
        return -1L
    }


    override fun part2(data: List<String>): Int {
        return data.flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to c } }
            .toMap()
            .compute()
    }

    private fun Map<Point, Char>.getKeyByValue(c: Char) = this.asIterable().first { it.value == c }.key


    private fun Map<Point, Char>.compute(): Int {
        data class P(val z: Point, val d: Point)
        data class Node(val p: P, val cost: Int, val path: List<Point>)

        val m = mutableSetOf<P>()
        val q = PriorityQueue<Node> { e1, e2 -> e1.cost.compareTo(e2.cost) }
        val z0 = this.getKeyByValue('S')
        q.add(Node(P(z0, Point(1, 0)), 0, listOf(z0)))
        val z1 = this.getKeyByValue('E')
        while (true) {
            val (p, cost, path) = q.poll()!!
            m.add(p)
            val (z, d) = p
            if (z == z1) {
                val hs = path.toMutableSet()
                generateSequence { q.poll()!! }
                    .takeWhile { q.isNotEmpty() && it.p.z == z1 && it.cost == cost }
                    .forEach { hs.addAll(it.path) }
                return hs.size
            }
            if (this[z + d] != '#' && P(z + d, d) !in m) q.add(Node(P(z + d, d), cost + 1, path + (z + d)))
            listOf(d * Point(0, -1), d * Point(0, 1))
                .filter { P(z, it) !in m }
                .forEach { q.add(Node(P(z, it), cost + 1000, path)) }
        }
    }
}


