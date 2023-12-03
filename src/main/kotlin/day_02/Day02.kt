package day_02


import cc.ekblad.konbini.ParserResult
import cc.ekblad.konbini.chain
import cc.ekblad.konbini.char
import cc.ekblad.konbini.integer
import cc.ekblad.konbini.oneOf
import cc.ekblad.konbini.parseToEnd
import cc.ekblad.konbini.parser
import cc.ekblad.konbini.whitespace
import cc.ekblad.konbini.string as stringp
import getDayInput
import println
import kotlin.time.measureTimedValue

val colorMap = mapOf(Pair(Color.RED, 12), Pair(Color.GREEN, 13), Pair(Color.BLUE, 14))

fun main() {
    val input = getDayInput(2).map { gameParser.parseToEnd(it).unwrap() }
//    val (p1Solution, p1Time) = measureTimedValue { part1(input) }
//    println("Part 1 solution: $p1Solution , Time taken: $p1Time")
//    val (p2Solution, p2Time) = measureTimedValue { part2(input) }
//    println("Part 2 solution: $p2Solution , Time taken: $p2Time")
    val game = gameParser.parseToEnd("Game 83: 2 red, 19 blue, 2 green; 5 red, 5 blue, 2 green; 2 red, 4 blue, 1 green\n").unwrap()
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
    val color = oneOf(stringp("red"), stringp("blue"), stringp("green"))
    Draw(amount.toInt(), color)
}

inline fun <reified T> ParserResult<T>.unwrap() = if (this is ParserResult.Ok) { this.result } else { throw RuntimeException() }

data class Draw(val amount: Int, val color: String)