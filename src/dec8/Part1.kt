package dec8

import java.io.File

private val NODE_MAP = mutableMapOf<String, Node>()
private val NODE_LINE_REGEX = Regex("(\\w+)\\s=\\s\\((\\w+),\\s(\\w+)\\)")
private const val STARTING_NODE = "AAA"
private const val TARGET_NODE = "ZZZ"

fun main() {
    File("inputs/dec8/input.txt").bufferedReader().use { reader ->
        val directions = reader.readLine()
        while (reader.ready()) {
            val currLine = reader.readLine()
            val match = NODE_LINE_REGEX.find(currLine)
            if (match !== null) {
                NODE_MAP[match.groups[1]!!.value] = Node(match.groups[1]!!.value, match.groups[2]!!.value, match.groups[3]!!.value)
            }
        }
        println(traverseToTarget(STARTING_NODE, TARGET_NODE, directions))
    }
}

private fun traverseToTarget(startingNode: String, targetNode: String, directions: String): Int {
    var dirIterator = directions.iterator()
    var currNode = NODE_MAP[startingNode]!!
    var numSteps = 0
    while (currNode != NODE_MAP[targetNode]) {
        if (!dirIterator.hasNext()) {
            dirIterator = directions.iterator()
        }
        currNode = if (dirIterator.nextChar() == 'L') NODE_MAP[currNode.left]!! else NODE_MAP[currNode.right]!!
        numSteps++
    }
    return numSteps
}
