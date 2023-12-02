package day_02

import cc.ekblad.konbini.*
import getDayInput
import println
import kotlin.time.measureTimedValue

val colorMap = mapOf(Pair(Color.RED, 12), Pair(Color.GREEN, 13), Pair(Color.BLUE, 14))

fun main() {
//    val input = getDayInput(2).map { Game.parse(it) }
//    val (p1Solution, p1Time) = measureTimedValue { part1(input) }
//    println("Part 1 solution: $p1Solution , Time taken: $p1Time")
//    val (p2Solution, p2Time) = measureTimedValue { part2(input) }
//    println("Part 2 solution: $p2Solution , Time taken: $p2Time")
    gameParser.parseToEnd("Game 83: 2 red, 19 blue, 2 green; 5 red, 5 blue, 2 green; 2 red, 4 blue, 1 green\n")
}

fun part1(input: List<Game>) = input.filter { it.satisfiableBy(colorMap) }
    .sumOf { it.id }
fun part2(input: List<Game>) = input.sumOf { it.power }

val gameParser = parser {
    string("Game")
    whitespace()
    val id = integer()
    char(':')
    whitespace()
    val rounds = chain(roundParser, cc.ekblad.konbini.char(';')).terms
    AlternateGame(id.toInt(), rounds)
}

val roundParser = parser {
    AlternateRound(chain(drawParser, cc.ekblad.konbini.char(',')).terms)
}

val drawParser = parser {
    whitespace()
    val amount = integer()
    whitespace()
    val color = oneOf(cc.ekblad.konbini.string("red"), cc.ekblad.konbini.string("blue"), cc.ekblad.konbini.string("green"))
    Draw(amount.toInt(), color)
}

data class Draw(val amount: Int, val color: String)