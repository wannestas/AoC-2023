import kotlin.time.measureTimedValue
import getDayInput

fun main() {
    val input = parseInput(getDayInput(2))
    val (p1Solution, p1Time) = measureTimedValue { part1(input) }
    println("Part 1 solution: $p1Solution , Time taken: $p1Time")
    val (p2Solution, p2Time) = measureTimedValue { part2(input) }
    println("Part 2 solution: $p2Solution , Time taken: $p2Time")
}

fun parseInput(input: List<String>): List<String> {
    return input
}

fun part1(input: List<String>) {

}
fun part2(input: List<String>) {

}