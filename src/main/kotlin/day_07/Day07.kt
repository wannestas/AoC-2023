package day_07

import DayTemplate
import cc.ekblad.konbini.char
import cc.ekblad.konbini.integer
import cc.ekblad.konbini.oneOf
import cc.ekblad.konbini.parseToEnd
import cc.ekblad.konbini.parser
import cc.ekblad.konbini.whitespace
import unwrap

fun main() {
    val day = Day07()
    day.main(7)
}
class Day07: DayTemplate<List<Hand>, List<Hand>>() {

    private val handParser = parser {
        val cards = listOf(cardParser(),
            cardParser(),
            cardParser(),
            cardParser(),
            cardParser())
        whitespace()
        val bet = integer().toInt()
        Hand(cards, bet)
    }

    val cardParser = parser {
        Card.parseFrom(char())
    }
    override fun parseInputPart1(input: List<String>): List<Hand> {
        return input.map { handParser.parseToEnd(it).unwrap() }
    }
    override fun part1(input: List<Hand>): Int {
        return input.sorted()
            .foldIndexed(0) {index, acc, hand ->
                acc + (index + 1) * hand.bid
             }
    }

    override fun parseInputPart2(input: List<String>): List<Hand> {
        return parseInputPart1(input)
    }

    override fun part2(input: List<Hand>): Long {
        return input.sortedWith(Comparator(Hand::compareToPart2))
            .foldIndexed(0) {index, acc, hand ->
                acc + (index + 1) * hand.bid
            }
    }

}

data class Hand(val cards: List<Card>, val bid: Int): Comparable<Hand> {
    private val handType: HandType
        get() {
            val groupTypes = cards.groupBy { it.name }.values.map { it.size }.map { it.toHandType() }
            if (groupTypes.contains(HandType.ONE_PAIR) && groupTypes.contains(HandType.THREE_OF_A_KIND)) return HandType.FULL_HOUSE
            if (groupTypes.count { it == HandType.ONE_PAIR } == 2) return HandType.TWO_PAIR
            return groupTypes.max()
        }
    private val handType2: HandType
        get() {
            val groupSizes = cards.filter { it != Card.JACK }.groupBy { it.name }.values.map { it.size }.sorted().toMutableList()
            if (groupSizes.size == 0) return HandType.FIVE_OF_A_KIND
            val previousMax = groupSizes.removeLast()
            val jokerCount = cards.count {it == Card.JACK}
            groupSizes.add(previousMax + jokerCount)
            val groupTypes = groupSizes.map { it.toHandType() }
            if (groupTypes.contains(HandType.ONE_PAIR) && groupTypes.contains(HandType.THREE_OF_A_KIND)) return HandType.FULL_HOUSE
            if (groupTypes.count { it == HandType.ONE_PAIR } == 2) return HandType.TWO_PAIR
            return groupTypes.max()
        }
    override fun compareTo(other: Hand): Int {
        val typeComparison = this.handType.compareTo(other.handType)
        if (typeComparison != 0) return typeComparison
        for (i in cards.indices) {
            val comparison = this.cards[i].compareTo(other.cards[i])
            if (comparison != 0) return comparison
        }
        return 0
    }

    fun compareToPart2(other: Hand): Int {
        val typeComparison = this.handType2.compareTo(other.handType2)
        if (typeComparison != 0) return typeComparison
        for (i in cards.indices) {
            val comparison = this.cards[i].compareTo2(other.cards[i])
            if (comparison != 0) return comparison
        }
        return 0
    }


    private fun Int.toHandType(): HandType {
        return when(this) {
            1 -> HandType.HIGH_CARD
            2 -> HandType.ONE_PAIR
            3 -> HandType.THREE_OF_A_KIND
            4 -> HandType.FOUR_OF_A_KIND
            5 -> HandType.FIVE_OF_A_KIND
            else -> throw IllegalArgumentException(this.toString())
        }
    }

    override fun toString(): String {
        return "Hand(cards=$cards, bid=$bid, handType=$handType)"
    }
}

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND,


}

enum class Card {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE;

    fun compareTo2(other: Card): Int {
        if (this == JACK && other != JACK) return -1
        if (this != JACK && other == JACK) return 1
        return this.compareTo(other)
    }

    companion object {
        fun parseFrom(char: Char): Card {
            return when (char) {
                'A' -> ACE
                'K' -> KING
                'Q' -> QUEEN
                'J' -> JACK
                'T' -> TEN
                '9' -> NINE
                '8' -> EIGHT
                '7' -> SEVEN
                '6' -> SIX
                '5' -> FIVE
                '4' -> FOUR
                '3' -> THREE
                '2' -> TWO
                else -> throw IllegalArgumentException(char.toString())
            }
        }
    }
}