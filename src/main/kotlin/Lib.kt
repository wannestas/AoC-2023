import kotlin.io.path.Path
import kotlin.io.path.readLines

fun getDayInput(day: Int): List<String> {
    val dayAsString = day.toString().padStart(2, '0')
    return Path("inputs/day-$dayAsString.txt").readLines()
}

fun Any?.println() = println(this)