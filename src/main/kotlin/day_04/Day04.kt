package day_04

import cc.ekblad.konbini.chain
import cc.ekblad.konbini.char
import cc.ekblad.konbini.integer
import cc.ekblad.konbini.parseToEnd
import cc.ekblad.konbini.parser
import cc.ekblad.konbini.whitespace
import kotlin.time.measureTimedValue
import getDayInput
import unwrap
import kotlin.math.pow

fun main() {
    val testInput = parseInput(getDayInput(4, true))
    val (p1TestSolution, p1TestTime) = measureTimedValue { part1(testInput) }
    println("Part 1 solution: $p1TestSolution , Time taken: $p1TestTime")
    val (p2TestSolution, p2TestTime) = measureTimedValue { part2(testInput) }
    println("Part 2 solution: $p2TestSolution , Time taken: $p2TestTime")

    val input = parseInput(getDayInput(4))
    val (p1Solution, p1Time) = measureTimedValue { part1(input) }
    println("Part 1 solution: $p1Solution , Time taken: $p1Time")
    val (p2Solution, p2Time) = measureTimedValue { part2(input) }
    println("Part 2 solution: $p2Solution , Time taken: $p2Time")

}

fun parseInput(input: List<String>): List<Card> {
    return input.map {  cardParser.parseToEnd(it).unwrap() }
}

val cardParser = parser {
    string("Card")
    whitespace()
    val cardId = integer()
    char(':')
    whitespace()
    val winningNumbers = chain(integer, whitespace).terms.map { it.toInt() }
    whitespace()
    char('|')
    whitespace()
    val pulledNumbers = chain(integer, whitespace).terms.map { it.toInt() }
    Card(cardId.toInt(), winningNumbers, pulledNumbers)
}

data class Card(val cardId: Int, val winningNumbers: List<Int>, val pulledNumbers: List<Int>) {
    val value: Int
        get() {
            val amountWon = pulledNumbers.count { winningNumbers.contains(it) }
            if (amountWon == 0) return 0
            return 2.toDouble().pow(amountWon - 1).toInt()
        }

    val amountWon: Int
        get() = pulledNumbers.count {winningNumbers.contains(it)}
}

fun part1(input: List<Card>): Int {

    return input.sumOf { it.value }
}
fun part2(input: List<Card>): Int {
    val copies = MutableList(input.size) {1}
    for (i in input.indices) {
        val amountWon = input[i].amountWon

        for (j in (i+1)..(i+amountWon)) {
            copies[j] += copies[i]
        }
    }

    return copies.sum()
}