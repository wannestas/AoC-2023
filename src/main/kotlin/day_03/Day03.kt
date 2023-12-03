package day_03

import kotlin.time.measureTimedValue
import getDayInput
import println

fun main() {
    val testInput = parseInput(getDayInput(3, true))
    val (p1TestSolution, p1TestTime) = measureTimedValue { part1(testInput) }
    println("Part 1 solution: $p1TestSolution , Time taken: $p1TestTime")
    val (p2TestSolution, p2TestTime) = measureTimedValue { part2(testInput) }
    println("Part 2 solution: $p2TestSolution , Time taken: $p2TestTime")

    val input = parseInput(getDayInput(3))
    val (p1Solution, p1Time) = measureTimedValue { part1(input) }
    println("Part 1 solution: $p1Solution , Time taken: $p1Time")
    val (p2Solution, p2Time) = measureTimedValue { part2(input) }
    println("Part 2 solution: $p2Solution , Time taken: $p2Time")

}

fun parseInput(input: List<String>): List<String> {
    return input
}

fun part1(input: List<String>): Int {
    val active = MutableList(input.size) { MutableList(input.first().length) { false} }
    val numbers: MutableList<StoredNumber> = mutableListOf()
    for (i in input.indices) {
        for (j in input[i].indices) {
            val c = input[i][j]
            if (!c.isDigit() && c != '.') {
                active.getClamped(i - 1).setClamped(j - 1, true)
                active.getClamped(i - 1).setClamped(j, true)
                active.getClamped(i - 1).setClamped(j + 1, true)
                active.getClamped(i).setClamped(j - 1, true)
                active.getClamped(i).setClamped(j, true)
                active.getClamped(i).setClamped(j + 1, true)
                active.getClamped(i + 1).setClamped(j - 1, true)
                active.getClamped(i + 1).setClamped(j, true)
                active.getClamped(i + 1).setClamped(j + 1, true)
            }
            if (! c.isDigit()) continue
            if (numbers.isEmpty() || numbers.last().height != i || j != numbers.last().location.last +1) {
                numbers.add(StoredNumber("$c".toInt(), j..j, i))
            } else {
                numbers.last().extend(j, "$c".toInt())
            }
        }
    }
    return numbers.filter { it.location.any { col -> active[it.height][col] } }
        .sumOf { it.num }
}
fun part2(input: List<String>): Int {
    val numbers: MutableList<StoredNumber> = mutableListOf()
    for (i in input.indices) {
        for (j in input[i].indices) {
            val c = input[i][j]
            if (! c.isDigit()) continue
            if (numbers.isEmpty() || numbers.last().height != i || j != numbers.last().location.last +1) {
                numbers.add(StoredNumber("$c".toInt(), j..j, i))
            } else {
                numbers.last().extend(j, "$c".toInt())
            }
        }
    }
    var sum = 0
    for (i in input.indices) {
        for (j in input[i].indices) {
            val c = input[i][j]
            if (c != '*') continue
            val touchingNumbers = numbers.filter { ((i-1)..(i+1)).contains(it.height) && ((j-1)..(j+1)).any { index -> it.location.contains(index)} }
            println("gaming")
            if (touchingNumbers.size != 2 ) continue
            sum += touchingNumbers.first().num * touchingNumbers.last().num
        }
    }
    return sum
}

fun <T> MutableList<T>.setClamped(i: Int, value: T) {
    this[i.coerceIn(this.indices)] = value
}

fun <T> List<T>.getClamped(i: Int): T {
    return this[i.coerceIn(this.indices)]

}

class StoredNumber(var num: Int, var location: IntRange, val height: Int) {

    fun extend(i: Int, value: Int) {
        location = when (i) {
            location.first -1 -> {
                num = "$value$num".toInt()
                i..(location.last)
            }
            location.last +1 -> {
                num = "$num$value".toInt()
                (location.first)..i
            }
            else -> throw IllegalArgumentException()

        }
    }

    override fun toString(): String {
        return "StoredNumber(num=$num, location=$location, height=$height)"
    }


}
