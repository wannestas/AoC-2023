package day_05

import cc.ekblad.konbini.chain
import cc.ekblad.konbini.integer
import cc.ekblad.konbini.parseToEnd
import cc.ekblad.konbini.parser
import cc.ekblad.konbini.regex
import cc.ekblad.konbini.whitespace
import getDayInput
import unwrap
import kotlin.time.measureTimedValue
import kotlin.math.max as valueMax
import kotlin.math.min as valueMin


fun main() {
    val testInput = parseInput(getDayInput(5, true))
    val (p1TestSolution, p1TestTime) = measureTimedValue { part1(testInput) }
    println("Part 1 solution: $p1TestSolution , Time taken: $p1TestTime")
    val (p2TestSolution, p2TestTime) = measureTimedValue { part2(testInput) }
    println("Part 2 solution: $p2TestSolution , Time taken: $p2TestTime")

    val input = parseInput(getDayInput(5))
    val (p1Solution, p1Time) = measureTimedValue { part1(input) }
    println("Part 1 solution: $p1Solution , Time taken: $p1Time")
    val (p2Solution, p2Time) = measureTimedValue { part2(input) }
    println("Part 2 solution: $p2Solution , Time taken: $p2Time")
}

fun parseInput(input: List<String>): Pair<List<Long>, List<MultiRangeMap>> {
    return seedParser.parseToEnd(input.joinToString(separator = "\n")).unwrap()
}

val seedParser = parser {
    string("seeds:")
    whitespace()
    val seeds = chain(integer, whitespace).terms
    whitespace()
    val maps = chain(mapParser, whitespace).terms
    seeds to maps
}

val mapParser = parser {
    val name = regex("([a-z]*-[a-z]*-[a-z]*)")
    whitespace()
    string("map:")
    whitespace()
    val mappings = chain(rangeParser, whitespace).terms
    MultiRangeMap(name, mappings)
}
data class MultiRangeMap(val name: String, val rangeMaps: List<RangeMapping>) {
    fun get(key: Long): Long {
        return rangeMaps.firstOrNull { it.keyRange.contains(key) }?.get(key) ?: key
    }

    fun getRange(keys: LongRange): List<LongRange> {
        val returnValues: MutableList<LongRange> = mutableListOf()
        val overlappingMaps = rangeMaps.filter { it.keyRange.overlapsWith(keys) }.sortedBy { it.keyStart }
        if (overlappingMaps.isEmpty()) return listOf(keys)
        var previousLast = keys.first
        for (i in overlappingMaps.indices) {
            val currentMap = overlappingMaps[i]
            returnValues.add(previousLast..<currentMap.keyStart)
            returnValues.add(currentMap.getRange(keys)!!)
            previousLast = currentMap.keyRange.last
        }
        returnValues.add(overlappingMaps.last().keyRange.last..keys.last)
        return returnValues.filter { it.isValid() }
    }
}

val rangeParser = parser {
    val valueStart = integer()
    whitespace()
    val keyStart = integer()
    whitespace()
    val range = integer()
    RangeMapping(valueStart, keyStart, range)
}
data class RangeMapping(val valueStart: Long, val keyStart: Long, val length: Long) {
    val keyRange = keyStart..<keyStart + length
    private val difference = valueStart - keyStart
    fun get(key: Long): Long {
        if (!keyRange.contains(key)) throw IllegalArgumentException(key.toString())
        return key + difference
    }

    fun getRange(keys: LongRange): LongRange? {
        return keys.coerceInto(keyRange)?.shift(difference)
    }
}

fun LongRange.coerceInto(range: LongRange): LongRange? {
    if (!this.overlapsWith(range)) return null
    return (valueMax(range.first, this.first)..(valueMin(range.last, this.last)))
}
fun LongRange.shift(shift: Long) = (this.first + shift)..(this.last + shift)
fun LongRange.overlapsWith(range: LongRange) = this.contains(range.first) || this.contains(range.last) || range.contains(this.first) || range.contains(this.last)
fun LongRange.isValid() = this.first <= this.last
fun part1(input: Pair<List<Long>, List<MultiRangeMap>>): Long {
    var values = input.first
    val maps = input.second
    for (map in maps) {
        values = values.map { map.get(it) }
    }
    return values.min()
}
fun part2(input: Pair<List<Long>, List<MultiRangeMap>>): Long {
    val values = input.first
    var ranges: MutableList<LongRange> = mutableListOf()
    val maps = input.second
    for (i in values.indices step 2) {
        val range = values[i]..<(values[i] + values[i+1])
        ranges.add(range)
    }
    for (map in maps) {
        ranges = ranges.map { map.getRange(it) }.flatten().toMutableList()
        ranges = mergeRanges(ranges).toMutableList()
    }
    return ranges.map { it.first }.min()
}

fun mergeRanges(ranges: List<LongRange>): List<LongRange> {
    val result: MutableList<LongRange> = mutableListOf()
    val sortedRanges = ranges.sortedBy { it.first }
    result.add(sortedRanges.first())
    for (i in 1..<sortedRanges.size) {
        val lastRange = result.removeLast()
        val currentRange = sortedRanges[i]
        if (lastRange.last == currentRange.first) {
            result.add(lastRange.first..currentRange.last)
        } else {
            result.add(lastRange)
            result.add(currentRange)
        }
    }
    return result
}