package day_01

import getDayInput
import kotlin.time.measureTimedValue

fun main() {
    val input = parseInput(getDayInput(1))
    val (p1Solution, p1Time) = measureTimedValue { part1(input) }
    println("Part 1 solution: $p1Solution , Time taken: $p1Time")
    val (p2Solution, p2Time) = measureTimedValue { part2(input) }
    println("Part 2 solution: $p2Solution , Time taken: $p2Time")
}

fun parseInput(input: List<String>): List<String> {
    return input
}

fun part1(input: List<String>): Int {
    return input.sumOf { line ->
        val digits = line.filter { it.isDigit() }
        "${digits.first()}${digits.last()}".toInt()
    }

}
fun part2(input: List<String>): Int {
    return input.map { it.replace("one", "o1e")
        .replace("two", "t2o")
        .replace("three", "th3ee")
        .replace("four", "4our")
        .replace("five", "5ive")
        .replace("six", "6ix")
        .replace("seven", "se7en")
        .replace("eight", "ei8ht")
        .replace("nine", "ni9e") }
        .sumOf { line ->
            val digits = line.filter { it.isDigit() }
            "${digits.first()}${digits.last()}".toInt()
        }
}