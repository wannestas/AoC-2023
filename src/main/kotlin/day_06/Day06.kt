package day_06

import DayTemplate
import cc.ekblad.konbini.chain
import cc.ekblad.konbini.integer
import cc.ekblad.konbini.parseToEnd
import cc.ekblad.konbini.parser
import cc.ekblad.konbini.whitespace
import kotlin.time.measureTimedValue
import getDayInput
import unwrap
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

class Day06: DayTemplate<List<Race>, Race>() {


    override fun part1(input: List<Race>): Int {
        return input.map { it.beatingTimes }.map { it.last - it.first + 1 }.reduce(Int::times)
    }

    override fun part2(input: Race): Int {
        println(input)
        return input.beatingTimes.last - input.beatingTimes.first + 1
    }

    override fun parseInputPart1(input: List<String>): List<Race> {
        return inputParser.parseToEnd(input.joinToString("\n"))
            .unwrap()
            .map { Race(it.first.toDouble(), it.second.toDouble()) }
    }

    private val inputParser = parser {
        string("Time:")
        whitespace()
        val times = chain(integer, whitespace).terms.map { it.toInt() }
        whitespace()
        string("Distance:")
        whitespace()
        val distances = chain(integer, whitespace).terms.map { it.toInt() }
        times.zip(distances)
    }

    override fun parseInputPart2(input: List<String>): Race {
        return Race(input.first().filter { it.isDigit() }.toDouble(), input.last().filter { it.isDigit() }.toDouble())
    }




}

data class Race(val duration: Double, val minimumDistance: Double) {
//    x * (duration - x) > minimumDistance <=> -x^2 + duration * x - minimumDistance > 0
    val beatingTimes: IntRange
    get() {
        val discriminant = duration.pow(2) - 4 * minimumDistance
        val x0 = (-duration + sqrt(discriminant))/(-2)
        val x1 = (-duration - sqrt(discriminant))/(-2)
        return  floor(x0 + 1).toInt()..ceil(x1-1).toInt()
    }

    override fun toString(): String {
        return "Race(duration=$duration, minimumDistance=$minimumDistance)"
    }


}

fun main() {
    val day = Day06()
    day.main(6)
}