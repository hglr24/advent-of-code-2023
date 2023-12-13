package dec5

import java.io.File

private val NUM_LINE_REGEX = Regex("(\\d+)\\s(\\d+)\\s(\\d+)")

lateinit var seeds: Array<Long>
lateinit var isConverted: Array<Boolean>

fun main() {
    val reader = File("inputs/dec5/input.txt").bufferedReader()
    seeds = reader.readLine().substring(7).split(' ').map { it.toLong() }.toTypedArray()
    isConverted = Array(seeds.size) { false }

    while (reader.ready()) {
        val line = reader.readLine()
        if (line.isEmpty()) {
            isConverted = isConverted.map { false }.toTypedArray() // Reset tracking array after each conversion group
        }
        val match = NUM_LINE_REGEX.find(line)
        if (match != null) {
            processNumLine(match)
        }
    }
    println(seeds.min())
}

private fun processNumLine(match: MatchResult) {
    val sourceStart = match.groups[2]!!.value.toLong()
    val destStart = match.groups[1]!!.value.toLong()
    val rangeLength = match.groups[3]!!.value.toLong()

    seeds.forEachIndexed { i, it ->
        if (!isConverted[i]) {
            if ((sourceStart until (sourceStart + rangeLength)).contains(it)) {
                seeds[i] = destStart + (seeds[i] - sourceStart)
                isConverted[i] = true
            }
        }
    }
}
