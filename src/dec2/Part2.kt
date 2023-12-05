package dec2

import java.io.File

private val SINGLE_COLOR_VALUE_REGEX = Regex("(\\d+)\\s(red|blue|green)")

fun main() {
    var powerSum = 0
    File("inputs/dec2/input.txt").forEachLine {
        powerSum += calculateGamePower(it)
    }
    println(powerSum)
}

private fun calculateGamePower(line: String): Int {
    val maxValues = mutableMapOf(
        "red" to 0,
        "blue" to 0,
        "green" to 0
    )

    var currIndex = 0

    while (true) {
        val nextMatch = SINGLE_COLOR_VALUE_REGEX.find(line, currIndex) ?: break
        val currColor = nextMatch.groups[2]!!.value
        val currNumCubes = nextMatch.groups[1]!!.value.toInt()
        maxValues[currColor] = maxOf(currNumCubes, maxValues[currColor]!!)
        currIndex = nextMatch.range.last
    }

    return maxValues["red"]!! * maxValues["blue"]!! * maxValues["green"]!!
}
