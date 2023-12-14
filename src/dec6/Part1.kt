package dec6

import java.io.File
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    File("inputs/dec6/input.txt").bufferedReader().use { reader ->
        var totalProduct = 1
        val times = reader.readLine().split(Regex("\\s+")).drop(1).map { it.toInt() }.toTypedArray()
        val distances = reader.readLine().split(Regex("\\s+")).drop(1).map { it.toInt() }.toTypedArray()
        times.indices.forEach {
            totalProduct *= calculateNumWins(times[it], distances[it])
        }
        println(totalProduct)
    }
}

private fun calculateNumWins(time: Int, distance: Int): Int {
    val c0 = ceil(((time + sqrt(time.toDouble().pow(2.0) - (4 * (distance)))) / 2)).toInt()
    val c1 = floor(((time - sqrt(time.toDouble().pow(2.0) - (4 * (distance)))) / 2)).toInt()
    return c0 - c1 - 1
}
