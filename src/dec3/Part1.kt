package dec3

import java.io.File
import java.util.LinkedList

private val SYMBOL_REGEX = Regex("[^\\d\\.]")
private val NUMBER_REGEX = Regex("\\d+")

private var partsSum = 0

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
        findPartsInLine(buffer)
    }

    println(partsSum)
}

private fun findPartsInLine(buffer: LinkedList<String?>) {
    // List of every number on this line
    val numbers = NUMBER_REGEX.findAll(buffer[1]!!)

    if (numbers.none()) {
        return
    }

    // Indices of all symbols on previous, current, and next lines
    val prevLineSymbolIndices = SYMBOL_REGEX.findAll(buffer[0] ?: "").map { it.range.first }.toSet()
    val currLineSymbolIndices = SYMBOL_REGEX.findAll(buffer[1] ?: "").map { it.range.first }.toSet()
    val nextLineSymbolIndices = SYMBOL_REGEX.findAll(buffer[2] ?: "").map { it.range.first }.toSet()

    numbers.forEach { numberMatch ->
        val indexRange = numberMatch.range
        (indexRange.first - 1..indexRange.last + 1).forEach {
            if (prevLineSymbolIndices.contains(it) || currLineSymbolIndices.contains(it) || nextLineSymbolIndices.contains(it)) {
                partsSum += numberMatch.value.toInt()
            }
        }
    }
}
