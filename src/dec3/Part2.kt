package dec3

import java.io.File
import java.util.LinkedList

private val GEAR_REGEX = Regex("\\*")
private val NUMBER_REGEX = Regex("\\d+")

private var gearRatioSum = 0

fun main() {
    val reader = File("inputs/dec3/input.txt").bufferedReader()
    val buffer: LinkedList<String?> = LinkedList()

    // Pre-stage
    buffer.add(null)
    buffer.add(null)
    buffer.add(reader.readLine())

    while (buffer[2] != null) {
        buffer.removeFirst()
        buffer.add(reader.readLine())
        findGearsInLine(buffer)
    }

    println(gearRatioSum)
}

private fun findGearsInLine(buffer: LinkedList<String?>) {
    // List of every gear index on this line
    val gearIndices = GEAR_REGEX.findAll(buffer[1]!!).map { it.range.first }

    if (gearIndices.none()) {
        return
    }

    // All numbers on previous, current, and next lines. If I wanted to be fancy I could cache this data between iterations.
    val numbers = NUMBER_REGEX.findAll(buffer[0] ?: "")
        .plus(NUMBER_REGEX.findAll(buffer[1] ?: ""))
        .plus(NUMBER_REGEX.findAll(buffer[2] ?: ""))

    gearIndices.forEach { gearIndex ->
        val adjacentNumbers = mutableListOf<MatchResult>()
        numbers.forEach {
            if (it.range.contains(gearIndex - 1) || it.range.contains(gearIndex) || it.range.contains(gearIndex + 1)) {
                adjacentNumbers.add(it)
            }
        }

        if (adjacentNumbers.size == 2) {
            gearRatioSum += adjacentNumbers[0].value.toInt() * adjacentNumbers[1].value.toInt()
        }
    }
}
