package day5

import util.getInputText

typealias Stacks = Map<String, List<String>>

fun main() {
    println(part1(getInputText(5)))
}

fun part1(input: String): String {
    val (stacksInput, instructionsInput) = input.split("\n\n")

    val stacks = parseStacks(stacksInput)
    val instructions = instructionsInput.split("\n").map { it.split(" ") }

    val modifiedStacks = instructions.fold(stacks) { acc, instruction ->
        val count = instruction[1]
        val from = instruction[3]
        val to = instruction[5]

        (1..count.toInt()).fold(acc) { acc2, _ ->
            acc2.moveCrate(from, to)
        }
    }

    return modifiedStacks.values.joinToString("") { it.last() }
}

fun Stacks.moveCrate(fromLabel: String, toLabel: String): Stacks {
    val fromStack = this[fromLabel]!!
    val toStack = this[toLabel]!!

    val fromStackWithLastItemRemoved = fromStack.dropLast(1)
    val toStackWithLastItemOfFromStack = toStack + fromStack.last()
    return this +
            (fromLabel to fromStackWithLastItemRemoved) +
            (toLabel to toStackWithLastItemOfFromStack)
}

fun parseStacks(input: String): Stacks {
    val lines = input.split("\n")

    val headingsAndIndexes = lines.last().foldIndexed<Map<String, Int>>(emptyMap()) { index, acc, c ->
        val label = c.toString()
        if (label.isBlank()) acc else acc + (label to index)
    }

    return headingsAndIndexes.map { (label, index) ->
        val crates = lines.reversed().drop(1).map {
            it.getOrElse(index) { ' ' }.toString()
        }.filter { it.isNotBlank() }

        label to crates
    }.toMap()
}