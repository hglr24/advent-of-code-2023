package dec4

import java.io.File
import java.util.LinkedList

private val NUMBER_REGEX = Regex("\\d+")
private val cachedMatchCounts = LinkedList<Int>()

private const val WINNING_NUM_COUNT = 10
private const val PLAYER_NUM_COUNT = 25

private var cardSum = 0

fun main() {
    File("inputs/dec4/input.txt").forEachLine {
        cachedMatchCounts.add(findNumPlayerMatches(it))
    }
    cachedMatchCounts.addFirst(cachedMatchCounts.size) // O(1) prepend to LinkedList
    recurseCard(cachedMatchCounts.toTypedArray(), 0)
    println(cardSum)
}

private fun recurseCard(array: Array<Int>, currIndex: Int) {
    if (currIndex >= array.size || array[currIndex] == 0) {
        return
    }

    for (i in currIndex + 1..currIndex + array[currIndex]) {
        recurseCard(array, i)
    }

    cardSum += array[currIndex]
}

private fun findNumPlayerMatches(card: String): Int {
    val numbers = NUMBER_REGEX.findAll(card).map { it.value }.toList()
    val winningNums = numbers.slice(1..WINNING_NUM_COUNT).toHashSet()
    val playerNums = numbers.slice(WINNING_NUM_COUNT + 1..WINNING_NUM_COUNT + PLAYER_NUM_COUNT)

    return playerNums.filter { winningNums.contains(it) }.size
}
