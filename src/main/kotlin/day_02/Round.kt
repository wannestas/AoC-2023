package day_02

data class Round(val colorMap: Map<Color, Int>) {

    fun satisfiableBy(otherColorMap: Map<Color, Int>): Boolean {
        return otherColorMap.all {
            colorMap.getValue(it.key) <= it.value
        }
    }

    companion object {
        fun parse(string: String): Round {
            val colorMap = mutableMapOf<Color, Int>().withDefault { 0 }
            string.split(",")
                .map { it.trim() }
                .forEach {
                    val splitOnSpace = it.split(" ")
                    colorMap[Color.valueOf(splitOnSpace.last().uppercase())] = splitOnSpace.first().toInt()
                }
            return Round(colorMap)
        }
    }
}
