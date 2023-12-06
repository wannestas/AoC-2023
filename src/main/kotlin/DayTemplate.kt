import kotlin.time.measureTimedValue

abstract class DayTemplate<T, G> {
    fun main(day: Int) {
        val testInput = getDayInput(day, true)
        runInput(testInput)

        val input = getDayInput(day)
        runInput(input)
    }

    private fun runInput(input: List<String>) {
        val part1Input = parseInputPart1(input)
        val (p1Solution, p1Time) = measureTimedValue { part1(part1Input) }
        println("Part 1 solution: $p1Solution , Time taken: $p1Time")
        val part2Input = parseInputPart2(input)
        val (p2Solution, p2Time) = measureTimedValue { part2(part2Input) }
        println("Part 2 solution: $p2Solution , Time taken: $p2Time")
    }

    abstract fun parseInputPart1(input: List<String>): T
    abstract fun parseInputPart2(input: List<String>): G

    abstract fun part1(input: T): Int
    abstract fun part2(input: G): Int
}