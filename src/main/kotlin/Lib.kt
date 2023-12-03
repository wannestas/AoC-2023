import kotlin.io.path.Path
import kotlin.io.path.readLines

fun getDayInput(day: Int, test: Boolean = false): List<String> {
    val dayAsString = day.toString().padStart(2, '0')
    if (!test)
        return Path("inputs/day-$dayAsString.txt").readLines()
    return Path("inputs/day-$dayAsString-test.txt").readLines()
}

fun Any?.println() = println(this)