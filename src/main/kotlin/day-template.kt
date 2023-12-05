import kotlin.time.measureTimedValue
import getDayInput

fun main() {
    val testInput = parseInput(getDayInput(2, true))
    val (p1TestSolution, p1TestTime) = measureTimedValue { part1(testInput) }
    println("Part 1 solution: $p1TestSolution , Time taken: $p1TestTime")
    val (p2TestSolution, p2TestTime) = measureTimedValue { part2(testInput) }
    println("Part 2 solution: $p2TestSolution , Time taken: $p2TestTime")

    val input = parseInput(getDayInput(2))
    val (p1Solution, p1Time) = measureTimedValue { part1(input) }
    println("Part 1 solution: $p1Solution , Time taken: $p1Time")
    val (p2Solution, p2Time) = measureTimedValue { part2(input) }
    println("Part 2 solution: $p2Solution , Time taken: $p2Time")
}

fun parseInput(input: List<String>): List<String> {
    return input
}

fun part1(input: List<String>): Int {

    return 0
}
fun part2(input: List<String>): Int {

    return 0
}