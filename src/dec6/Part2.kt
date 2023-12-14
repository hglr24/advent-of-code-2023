package dec6

import java.io.File
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    File("inputs/dec6/input.txt").bufferedReader().use { reader ->
        val time = reader.readLine().split(Regex("\\s+")).drop(1).joinToString("") { it }.toLong()
        val distance = reader.readLine().split(Regex("\\s+")).drop(1).joinToString("") { it }.toLong()
        println(calculateNumWins(time, distance))
    }
}

private fun calculateNumWins(time: Long, distance: Long): Long {
    val c0 = ceil(((time + sqrt(time.toDouble().pow(2.0) - (4 * (distance)))) / 2)).toInt()
    val c1 = floor(((time - sqrt(time.toDouble().pow(2.0) - (4 * (distance)))) / 2)).toInt()
    return c0 - c1 - 1L
}
