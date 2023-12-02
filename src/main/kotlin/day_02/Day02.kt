package day_02

import getDayInput
import kotlin.time.measureTimedValue

val colorMap = mapOf(Pair(Color.RED, 12), Pair(Color.GREEN, 13), Pair(Color.BLUE, 14))

fun main() {
    val input = getDayInput(2).map { Game.parse(it) }
    val (p1Solution, p1Time) = measureTimedValue { part1(input) }
    println("Part 1 solution: $p1Solution , Time taken: $p1Time")
    val (p2Solution, p2Time) = measureTimedValue { part2(input) }
    println("Part 2 solution: $p2Solution , Time taken: $p2Time")
}

fun part1(input: List<Game>) = input.filter { it.satisfiableBy(colorMap) }
    .sumOf { it.id }
fun part2(input: List<Game>) = input.sumOf { it.power }

