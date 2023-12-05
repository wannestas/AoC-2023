import cc.ekblad.konbini.ParserResult
import kotlin.io.path.Path
import kotlin.io.path.readLines

fun getDayInput(day: Int, test: Boolean = false): List<String> {
    val dayAsString = day.toString().padStart(2, '0')
    if (!test)
        return Path("inputs/day-$dayAsString.txt").readLines()
    return Path("inputs/day-$dayAsString-test.txt").readLines()
}

fun Any?.println() = println(this)

inline fun <reified T> ParserResult<T>.unwrap() = if (this is ParserResult.Ok) { this.result } else { throw RuntimeException(this.toString()) }
