package dec2

import java.io.File

private val COLOR_COUNT_MAP = mapOf(
    "red" to 12,
    "blue" to 14,
    "green" to 13
)

private val SINGLE_COLOR_VALUE_REGEX = Regex("(\\d+)\\s(red|blue|green)")
private val GAME_NUMBER_REGEX = Regex("\\d+")

fun main() {
    var gameNumberSum = 0
    File("inputs/dec2/input.txt").forEachLine {
        if(assertValidGame(it)) {
            gameNumberSum += GAME_NUMBER_REGEX.find(it)!!.value.toInt()
        }
    }
    println(gameNumberSum)
}

private fun assertValidGame(line: String): Boolean {
    var currIndex = 0
    while (true) {
        val nextMatch = SINGLE_COLOR_VALUE_REGEX.find(line, currIndex) ?: break

        if (nextMatch.groups[1]!!.value.toInt() > COLOR_COUNT_MAP[nextMatch.groups[2]!!.value]!!) {
            return false
        }
        currIndex = nextMatch.range.last
    }
    return true
}
