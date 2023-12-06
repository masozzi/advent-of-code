import java.io.File

fun main() {
    part1()
    part2()
}

fun part1() {
    val lines = File("input.txt").readLines()
    if (lines.size != 2) {
        throw Exception("Invalid input from file")
    }

    val times = lines[0].split(":")[1].split(" ").stream()
            .filter { it.isNotBlank() }
            .map { it.trim().toInt() }
            .toList()

    val distances = lines[1].split(":")[1].split(" ").stream()
            .filter { it.isNotBlank() }
            .map { it.trim().toInt() }
            .toList()

    val wins = Array(times.size) { 0 }
    for ((i, v) in times.withIndex()) {
        for (t in 1..v) {
            val d = t * (v - t)
            if (d > distances[i]) wins[i] += 1
        }
    }

    println("TIME: $times DISTANCE: $distances")
    var res = 1
    for (w in wins) {
        res *= w
    }

    println("RES: $res")
}

fun part2() {
    val lines = File("input.txt").readLines()
    if (lines.size != 2) {
        throw Exception("Invalid input from file")
    }

    val time = lines[0].split(":")[1].filterNot { it.isWhitespace() }.toLong()
    val distance = lines[1].split(":")[1].filterNot { it.isWhitespace() }.toLong()

    var win: Long = 0
    for (t in 1..time) {
        val d = t * (time - t)
        if (d > distance) win++
    }

    println("RES: $win")
}