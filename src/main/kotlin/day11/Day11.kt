package day11

import util.getInputText
import util.getSampleInputText

fun main() {
    println(part1(getInputText(11)))
}

fun part1(input: String): Long {
    val monkeys = parse(input)

    repeat(20) {
        monkeys.forEach {
            val throws = it.takeTurn()
            monkeys.applyThrows(throws)
        }
    }

    return monkeys.map { it.inspections }.sortedDescending().take(2).let { it[0] * it[1] }
}


data class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspections: Long = 0
) {
    fun takeTurn(): List<Throw> {
        return items.map { item ->
            inspections++
            val newWorryLevel = operation(item) / 3

            Throw(
                item = newWorryLevel,
                toMonkey = if (test(newWorryLevel)) trueMonkey else falseMonkey
            )
        }.also { items.clear() }

    }
}

data class Throw(
    val item: Long,
    val toMonkey: Int
)

fun parse(input: String): List<Monkey> {
    return input.split("\n\n")
        .map {
            val lines = it.split("\n")
            val items = lines.getValue("Starting items").split(", ").map { it.trim().toLong() }
            val operation = lines.getValue("Operation").split(" ").takeLast(3)
            val testValue = lines.getValue("Test").split(" ").last().toLong()
            val trueMonkey = lines.getValue("true").split(" ").last().toInt()
            val falseMonkey = lines.getValue("false").split(" ").last().toInt()

            Monkey(
                items = items.toMutableList(),
                operation = getOperationFunction(operation),
                test = { it % testValue == 0L },
                trueMonkey = trueMonkey,
                falseMonkey = falseMonkey
            )
        }
}

private fun List<String>.getValue(key: String) =
    single { it.contains(key) }.split(":").last()

private fun getOperationFunction(operation: List<String>) = { it: Long ->
    val operand1 = if (operation[0] == "old") it else operation[0].toLong()
    val operand2 = if (operation[2] == "old") it else operation[2].toLong()

    when (operation[1]) {
        "+" -> operand1 + operand2
        "*" -> operand1 * operand2
        else -> TODO()
    }
}

private fun List<Monkey>.applyThrows(throws: List<Throw>) {
    throws.forEach {
        this[it.toMonkey].items.add(it.item)
    }
}