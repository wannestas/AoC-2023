package day_9

import DayTemplate
import cc.ekblad.konbini.chain
import cc.ekblad.konbini.integer
import cc.ekblad.konbini.parseToEnd
import cc.ekblad.konbini.parser
import cc.ekblad.konbini.whitespace
import println
import unwrap

fun main() {
    val day = Day09()
    day.main(9)
}

class Day09: DayTemplate<List<List<Long>>, List<List<Long>>>() {
    override fun parseInputPart1(input: List<String>): List<List<Long>> {
        return input.map { sequenceParser.parseToEnd(it).unwrap() }
    }
    private val sequenceParser = parser {
        chain(integer, whitespace).terms
    }

    override fun parseInputPart2(input: List<String>): List<List<Long>> {
        return parseInputPart1(input)
    }

    override fun part2(input:  List<List<Long>>): Long {
        return input.sumOf { it.extrapolateBackwards() }
    }

    override fun part1(input: List<List<Long>>): Long {
        return input.sumOf { it.extrapolate() }
    }

    fun List<Long>.extrapolate(): Long {
        val sequencesList = mutableListOf(this)
        while (sequencesList.last().any { it != 0.toLong() }) {
            sequencesList.add(sequencesList.last().reduceByOne {a, b -> b - a})
        }
        return sequencesList.sumOf { it.last() }
    }
    fun List<Long>.extrapolateBackwards(): Long {
        val sequencesList = mutableListOf(this)
        while (sequencesList.last().any { it != 0.toLong() }) {
            sequencesList.add(sequencesList.last().reduceByOne { a, b -> b - a })
        }
        return sequencesList.foldRight(0) {longs, acc -> longs.first() - acc}
    }

    fun <T, G> List<T>.reduceByOne(reduction: (T, T) -> G): List<G> {
        val newList: MutableList<G> = mutableListOf()
        for (i in 0..<(this.size-1)) {
            newList.add(i, reduction(this[i], this[i+1]))
        }
        return newList.toList()
    }
}