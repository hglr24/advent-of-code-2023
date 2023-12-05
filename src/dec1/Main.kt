package dec1

import java.io.File

private var sum = 0

private val FORWARD_INT_NAME_MAP = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)

private val REVERSE_INT_NAME_MAP = mapOf(
    "eno" to "1",
    "owt" to "2",
    "eerht" to "3",
    "ruof" to "4",
    "evif" to "5",
    "xis" to "6",
    "neves" to "7",
    "thgie" to "8",
    "enin" to "9"
)

private val FORWARD_DIGIT_REGEX = Regex("(?:\\d|one|two|three|four|five|six|seven|eight|nine)")
private val REVERSE_DIGIT_REGEX = Regex("(?:\\d|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin)")

fun main() {
    File("dec1/input.txt").forEachLine {
        parseLineAndAddToSum(it)
    }
    println(sum)
}

private fun parseLineAndAddToSum(line: String) {
    // Line always contains at least one digit
    val firstMatch = FORWARD_DIGIT_REGEX.find(line)!!.value
    val lastMatch = REVERSE_DIGIT_REGEX.find(line.reversed())!!.value
    val parsedInteger = FORWARD_INT_NAME_MAP.getOrDefault(firstMatch, firstMatch)
        .plus(REVERSE_INT_NAME_MAP.getOrDefault(lastMatch, lastMatch)).toInt()
    sum += parsedInteger
}
