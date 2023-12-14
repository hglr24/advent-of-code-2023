package dec5

import java.io.BufferedReader
import java.io.File
import java.util.SortedSet
import java.util.TreeSet

data class SourceNode(
    var start: Long,
    var range: Long
)

data class TransformNode(
    val sourceStart: Long,
    val range: Long,
    val destinationStart: Long
): Comparable<TransformNode> {
    fun appliesTo(sourceNode: SourceNode): Boolean {
        return sourceNode.start < (sourceStart + range) && (sourceNode.start + sourceNode.range) > sourceStart
    }

    override fun compareTo(other: TransformNode): Int {
        return sourceStart.compareTo(other.sourceStart)
    }
}

private val TRANSFORM_LINE_REGEX = Regex("(\\d+)\\s(\\d+)\\s(\\d+)")
private val SPACE_REGEX = Regex("\\s")

fun main() {
    var sourceNodes: MutableSet<SourceNode> = HashSet()
    File("inputs/dec5/input.txt").bufferedReader().use { reader ->
        val stringNums = reader.readLine().substring(7).split(SPACE_REGEX)
        for (i in stringNums.indices step 2) {
            sourceNodes.add(SourceNode(stringNums[i].toLong(), stringNums[i + 1].toLong()))
        }
        while (reader.ready()) { // Iterate until no more transform sets
            val currTransformNodes = parseTransformSet(reader)
            sourceNodes = augmentSourceNodes(sourceNodes, currTransformNodes)
        }
    }
    println(sourceNodes.minBy { it.start }.start)
}

private fun parseTransformSet(reader: BufferedReader): SortedSet<TransformNode> {
    // It's important to apply the transform nodes in sorted order by source start index to avoid more complex segmenting of sourceNode at apply time
    val transformNodes = TreeSet<TransformNode>()
    while (reader.ready()) {
        val currLine = reader.readLine()
        if (currLine.isEmpty()) {
            break
        }
        val match = TRANSFORM_LINE_REGEX.find(currLine)
        if (match != null) {
            transformNodes.add(TransformNode(match.groups[2]!!.value.toLong(), match.groups[3]!!.value.toLong(), match.groups[1]!!.value.toLong()))
        }
    }
    return transformNodes
}

private fun augmentSourceNodes(sourceNodes: Set<SourceNode>, transformNodes: SortedSet<TransformNode>): MutableSet<SourceNode> {
    val newSourceNodes = HashSet<SourceNode>()
    sourceNodes.forEach { sourceNode ->
        transformNodes.filter { it.appliesTo(sourceNode) }.forEach { transformNode ->
            if (sourceNode.start < transformNode.sourceStart) { // Take part of sourceNode if it starts before transformNode
                newSourceNodes.add(SourceNode(sourceNode.start, (transformNode.sourceStart - sourceNode.start)))
                sourceNode.range -= (transformNode.sourceStart - sourceNode.start)
                sourceNode.start = transformNode.sourceStart
            }                                                   // Take transformed section of sourceNode
            val newNodeRange = minOf(sourceNode.range, transformNode.sourceStart + transformNode.range - sourceNode.start)
            newSourceNodes.add(SourceNode((transformNode.destinationStart - transformNode.sourceStart) + sourceNode.start, newNodeRange))
            sourceNode.range -= newNodeRange
            sourceNode.start = transformNode.sourceStart + transformNode.range
        }
        if (sourceNode.range > 0) {                             // Take remaining sourceNode, if any
            newSourceNodes.add(sourceNode)
        }
    }
    return newSourceNodes
}
