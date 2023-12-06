package dec4

import java.io.File
import kotlin.math.pow

private val NUMBER_REGEX = Regex("\\d+")

fun main() {
    var totalPoints = 0
    File("inputs/dec4/input.txt").forEachLine {
        totalPoints += scoreScratchcard(it)
    }
    println(totalPoints)
}

private fun scoreScratchcard(card: String): Int {
    val numbers = NUMBER_REGEX.findAll(card).map { it.value }.toList()
    val winningNums = numbers.slice(1..10).toHashSet()
    val playerNums = numbers.slice(11..35)

    return (2.0).pow(playerNums.filter { winningNums.contains(it) }.size - 1).toInt()
}
