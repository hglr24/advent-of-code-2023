package dec7

data class Hand(
    val cards: String,
    val bid: Int,
    val type: HandType,
    val cardIndex: Map<Char, Int>
): Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        if (this.type == other.type) {
            var diffIndex = 0
            while ((diffIndex < this.cards.length - 1) && this.cards[diffIndex] == other.cards[diffIndex]) {
                diffIndex++
            }
            if (this.cards[diffIndex] == other.cards[diffIndex]) {
                return 0
            }
            return cardIndex[this.cards[diffIndex]]!!.compareTo(cardIndex[other.cards[diffIndex]]!!)
        } else {
            return this.type.weight.compareTo(other.type.weight)
        }
    }
}

enum class HandType(val weight: Int) {
    FIVE_OF_A_KIND(6),
    FOUR_OF_A_KIND(5),
    FULL_HOUSE(4),
    THREE_OF_A_KIND(3),
    TWO_PAIR(2),
    ONE_PAIR(1),
    HIGH_CARD(0);
}
