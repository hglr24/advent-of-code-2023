package dec7

import java.io.File
import java.util.SortedSet
import java.util.TreeSet

private val CARD_INDEX = mapOf(
    '2' to 0,
    '3' to 1,
    '4' to 2,
    '5' to 3,
    '6' to 4,
    '7' to 5,
    '8' to 6,
    '9' to 7,
    'T' to 8,
    'J' to -1,
    'Q' to 10,
    'K' to 11,
    'A' to 12
)

private val SPACE_REGEX = Regex("\\s")

fun main() {
    val sortedHands: SortedSet<Hand> = TreeSet()
    File("inputs/dec7/input.txt").bufferedReader().use { reader ->
        while (reader.ready()) {
            sortedHands.add(reader.readLine().split(SPACE_REGEX).let { Hand(it[0], it[1].toInt(), classifyHand(it[0]), CARD_INDEX) })
        }
    }
    var totalWinnings = 0
    sortedHands.forEachIndexed { index, hand -> totalWinnings += hand.bid * (index + 1) }
    println(totalWinnings)
}

private fun classifyHand(handString: String): HandType {
    val charMap = mutableMapOf<Char, Int>()
    handString.forEach {
        if (it != 'J') {
            charMap.putIfAbsent(it, 0)
            charMap[it] = charMap[it]!! + 1
        }
    }
    val editedHand = if (charMap.isNotEmpty()) handString.replace('J', charMap.maxBy { it.value }.key) else handString
    val distinctCards = editedHand.toCharArray().distinct()
    return when (distinctCards.size) {
        1 -> HandType.FIVE_OF_A_KIND
        2 -> {
            if (distinctCards.any { Regex(it.toString()).findAll(editedHand).count() == 4 }) {
                return HandType.FOUR_OF_A_KIND
            }
            return HandType.FULL_HOUSE
        }
        3 -> {
            if (distinctCards.any { Regex(it.toString()).findAll(editedHand).count() == 3 }) {
                return HandType.THREE_OF_A_KIND
            }
            return HandType.TWO_PAIR
        }
        4 -> HandType.ONE_PAIR
        5 -> HandType.HIGH_CARD
        else -> throw IllegalStateException("Card was somehow not classified as anything!")
    }
}
