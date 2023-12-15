package dec8

import java.io.File
import java.math.BigInteger

private val NODE_MAP = mutableMapOf<String, Node>()
private val NODE_LINE_REGEX = Regex("(\\w+)\\s=\\s\\((\\w+),\\s(\\w+)\\)")
private val STARTING_NODE_REGEX = Regex("\\w\\wA")
private val ENDING_NODE_REGEX = Regex("\\w\\wZ")

fun main() {
    File("inputs/dec8/input.txt").bufferedReader().use { reader ->
        val directions = reader.readLine()
        val startingNodes = mutableListOf<Node>()
        while (reader.ready()) {
            val currLine = reader.readLine()
            val match = NODE_LINE_REGEX.find(currLine)
            if (match !== null) {
                val currNodeName = match.groups[1]!!.value
                NODE_MAP[currNodeName] = Node(currNodeName, match.groups[2]!!.value, match.groups[3]!!.value)
                if (STARTING_NODE_REGEX.matches(currNodeName)) {
                    startingNodes.add(NODE_MAP[currNodeName]!!)
                }
            }
        }
        var cycleRangeLcm = BigInteger.ONE
        startingNodes.forEach {
            cycleRangeLcm = lcm(cycleRangeLcm, BigInteger.valueOf(findCycleFromStartingNode(it, directions)))
        }
        println(cycleRangeLcm)
    }
}

private fun findCycleFromStartingNode(startingNode: Node, directions: String): Long {
    val seenZIndices = mutableMapOf<Pair<Node, Int>, Long>()
    var dirIterator = directions.iterator().withIndex()
    var currNode = startingNode
    var numSteps = 0L
    while (true) {
        if (!dirIterator.hasNext()) {
            dirIterator = directions.iterator().withIndex()
        }
        val currDirection = dirIterator.next()
        if (ENDING_NODE_REGEX.matches(currNode.name)) {
            if (seenZIndices.containsKey(currNode to currDirection.index)) {
                return numSteps - seenZIndices[currNode to currDirection.index]!!
            }
            seenZIndices[currNode to currDirection.index] = numSteps
        }
        currNode = if (currDirection.value == 'L') NODE_MAP[currNode.left]!! else NODE_MAP[currNode.right]!!
        numSteps++
    }
}

private fun lcm(a: BigInteger, b: BigInteger): BigInteger {
    return a.multiply(b).abs().divide(a.gcd(b))
}
