package dec4

import java.io.File
import kotlin.math.pow

private val NUMBER_REGEX = Regex("\\d+")

private const val WINNING_NUM_COUNT = 10
private const val PLAYER_NUM_COUNT = 25

fun main() {
    var totalPoints = 0
    File("inputs/dec4/input.txt").forEachLine {
        totalPoints += scoreScratchcard(it)
    }
    println(totalPoints)
}

private fun scoreScratchcard(card: String): Int {
    val numbers = NUMBER_REGEX.findAll(card).map { it.value }.toList()
    val winningNums = numbers.slice(1..WINNING_NUM_COUNT).toHashSet()
    val playerNums = numbers.slice(WINNING_NUM_COUNT + 1..WINNING_NUM_COUNT + PLAYER_NUM_COUNT)

    return (2.0).pow(playerNums.filter { winningNums.contains(it) }.size - 1).toInt()
}
