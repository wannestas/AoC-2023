package day_08

import DayTemplate
import cc.ekblad.konbini.chain
import cc.ekblad.konbini.many
import cc.ekblad.konbini.oneOf
import cc.ekblad.konbini.parser
import cc.ekblad.konbini.whitespace
import cc.ekblad.konbini.char
import cc.ekblad.konbini.parseToEnd
import unwrap

fun main() {
    val day = Day08()
    day.main(8)
}

class Day08: DayTemplate<Pair<List<Direction>, Map<String, RoadFork>>, Pair<List<Direction>, Map<String, RoadFork>>>() {
    override fun parseInputPart1(input: List<String>): Pair<List<Direction>, Map<String, RoadFork>> {
        val pair = initialParser.parseToEnd(input.joinToString("\n")).unwrap()
        val forks = pair.second
        val forkMap: MutableMap<String, RoadFork> = mutableMapOf()
        forks.forEach {
            it.leftRoad = forks.first { oIt -> oIt.name == it.leftRoad!!.name}
            forkMap[it.name] = it
        }
        return pair.first to forkMap
    }

    val initialParser = parser {
        val directions = many(directionParser)
        whitespace()
        val roadForks = chain(forkParser, whitespace).terms
        directions to roadForks
    }

    private val directionParser = parser {
        Direction.of(oneOf(cc.ekblad.konbini.char('R'), cc.ekblad.konbini.char('L')))
    }

    private val forkParser = parser {
        val name = "${char()}${char()}${char()}"
        whitespace()
        char('=')
        whitespace()
        char('(')
        val fork1Name = "${char()}${char()}${char()}"
        char(',')
        whitespace()
        val fork2Name = "${char()}${char()}${char()}"
        char(')')
        RoadFork(name, RoadFork(fork1Name, null, null), RoadFork(fork2Name, null, null))
    }

    override fun parseInputPart2(input: List<String>): Pair<List<Direction>, Map<String, RoadFork>> {
        return parseInputPart1(input)
    }

    override fun part2(input: Pair<List<Direction>, Map<String, RoadFork>>): Long {
        val (directions, roads) = input
        var counter: Long = 0
        val loopLength: MutableMap<String, Long> = mutableMapOf()
        var currentRoads = roads.keys.filter { it.last() == 'A' }
        for (direction in directions.infiniteIterator()) {
            currentRoads = currentRoads.map { roads[it]!!.walk(direction)!!.name }
            counter += 1
            currentRoads.forEach {
                if (it.last() == 'Z' && !loopLength.containsKey(it)) loopLength[it] = counter
            }
            if (loopLength.values.size == currentRoads.size) break
        }
        println(currentRoads)
        println(loopLength)
        return loopLength.values.reduce {
            a, b -> a.leastCommonMultiple(b)
        }
    }
    fun Long.leastCommonMultiple(other: Long): Long {
        val larger = if (this > other) this else other
        val maxLcm = this * other
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % this == 0.toLong() && lcm % other == 0.toLong()) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    override fun part1(input: Pair<List<Direction>, Map<String, RoadFork>>): Int {
        var counter = 0
        var currentRoad = "AAA"
        val (directions, roads) = input
        for (direction in directions.infiniteIterator()) {
            currentRoad = roads[currentRoad]!!.walk(direction)!!.name
            counter += 1
            if (currentRoad == "ZZZ") break
        }
        return counter
    }

    fun <T> List<T>.infiniteIterator(): InfiniteIterator<T> {
        return InfiniteIterator(this.listIterator())
    }

}

class InfiniteIterator<out T>(private val iterator: ListIterator<T>): ListIterator<T> {


    override fun hasNext() = true
    override fun hasPrevious() = true

    override fun next(): T {
        if (iterator.hasNext()) return iterator.next()
        resetStart()
        return iterator.next()
    }

    override fun nextIndex(): Int {
        if (iterator.hasNext()) return iterator.nextIndex()
        resetStart()
        return iterator.nextIndex()
    }

    override fun previous(): T {
        if (iterator.hasPrevious()) return iterator.previous()
        resetEnd()
        return iterator.previous()
    }

    override fun previousIndex(): Int {
        if (iterator.hasPrevious()) return iterator.previousIndex()
        resetEnd()
        return iterator.previousIndex()
    }
    private fun resetStart() {
        while (iterator.hasPrevious()) iterator.previous()
    }

    private fun resetEnd() {
        while (iterator.hasNext()) iterator.next()
    }

}

enum class Direction {
    LEFT,
    RIGHT;

    companion object {
        fun of(char: Char): Direction {
            return when(char) {
                'R' -> RIGHT
                'L' -> LEFT
                else -> throw IllegalArgumentException(char.toString())
            }
        }
    }
}

class RoadFork(val name: String, var leftRoad: RoadFork?, var rightRoad: RoadFork?) {
    override fun toString(): String {
        return "RoadFork(name='$name', leftRoad=${leftRoad?.name}, rightRoad=${rightRoad?.name})"
    }

    fun walk(direction: Direction): RoadFork? {
        return when (direction) {
            Direction.LEFT -> leftRoad
            Direction.RIGHT -> rightRoad
        }
    }
}