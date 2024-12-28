package year2024

import model.DaySolver

class Day23Solver : DaySolver<List<String>, Any> {

    override fun part1(data: List<String>): Long {
        val graph = createGraph(data)
        return solve(graph, graph.keys.map { listOf(it) }.toSet(), 3).filter { l -> l.any {
            it.startsWith('t') }
         }.size.toLong()
    }

    override fun part2(data: List<String>): Any {
        val graph = createGraph(data)
        return solve(graph, graph.keys.map { listOf(it) }.toSet(), -1).first().joinToString(",")
    }

    private fun createGraph(data: List<String>): Map<String, MutableSet<String>> {
        val graph = mutableMapOf<String, MutableSet<String>>()
        for (l in data) {
            val (first, second) = l.split("-").map { it.trim() }
            graph.getOrPut(first) { mutableSetOf() }.add(second)
            graph.getOrPut(second) { mutableSetOf() }.add(first)
        }
        return graph
    }

    private fun solve(graph: Map<String, MutableSet<String>>, res: Set<List<String>>, level:Int): Set<List<String>> {
        val next = mutableSetOf<List<String>>()
        for (l in res) {
            val curr = graph[l[0]]!!
            for (c in curr) {
                if (c in l) continue
                if (graph[c]!!.containsAll(l)) {
                    next.add((l + c).sorted())
                }
            }
        }
        if (next.any { it.size == level }) return next
        return if (next.size == 0) return res else solve(graph, next, level)
    }

}





