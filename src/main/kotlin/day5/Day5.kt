package day5

import util.getInputText

typealias Stacks = Map<String, List<String>>

fun main() {
    println(part1(getInputText(5)))
    println(part2(getInputText(5)))
}

fun part1(input: String): String {
    val (stacksInput, instructionsInput) = input.split("\n\n")

    val stacks = parseStacks(stacksInput)
    val instructions = parseInstructions(instructionsInput)

    val modifiedStacks = instructions.fold(stacks) { acc, instruction ->
        (1..instruction.count).fold(acc) { acc2, _ ->
            acc2.moveCrates(1, instruction.from, instruction.to)
        }
    }

    return modifiedStacks.toMessage()
}

fun part2(input: String): String {
    val (stacksInput, instructionsInput) = input.split("\n\n")

    val stacks = parseStacks(stacksInput)
    val instructions = parseInstructions(instructionsInput)

    val modifiedStacks = instructions.fold(stacks) { acc, instruction ->
        acc.moveCrates(instruction.count, instruction.from, instruction.to)
    }

    return modifiedStacks.toMessage()
}

fun Stacks.moveCrates(count: Int, fromLabel: String, toLabel: String): Stacks {
    val fromStack = this[fromLabel]!!
    val toStack = this[toLabel]!!

    val fromStackWithLastItemsRemoved = fromStack.dropLast(count)
    val toStackWithLastItemsOfFromStack = toStack + fromStack.takeLast(count)

    return this +
            (fromLabel to fromStackWithLastItemsRemoved) +
            (toLabel to toStackWithLastItemsOfFromStack)
}

fun Stacks.toMessage() = values.joinToString("") { it.last() }

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

fun parseInstructions(input: String): List<Instruction> {
    return input.split("\n").map { it.split(" ") }
        .map {
            Instruction(
                count = it[1].toInt(),
                from = it[3],
                to = it[5]
            )
        }
}

data class Instruction(
    val count: Int,
    val from: String,
    val to: String
)