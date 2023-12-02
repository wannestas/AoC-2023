package day_02

import kotlin.time.times

data class Game(val id: Int, val rounds: List<Round>) {

    fun satisfiableBy(colorMap: Map<Color, Int>): Boolean {
        return rounds.all { it.satisfiableBy(colorMap) }
    }

    val power: Int
        get() = rounds.maxOf { it.colorMap.getValue(Color.RED) }
                .times(rounds.maxOf { it.colorMap.getValue(Color.BLUE)})
                .times(rounds.maxOf { it.colorMap.getValue(Color.GREEN)})


    companion object {
        fun parse(string: String): Game {
            val gameRoundsSplit = string.split(":").map { it.trim() }
            val id = gameRoundsSplit.first().split(" ").last().toInt()
            val rounds = gameRoundsSplit.last().split(";").map { Round.parse(it.trim()) }
            return Game(id, rounds)
        }
    }
}