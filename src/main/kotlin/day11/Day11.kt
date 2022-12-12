package day11

import util.getInputText
import java.math.BigInteger

fun main() {
    println(part1(getInputText(11)))
    println(part2(getInputText(11)))
}

fun part1(input: String): BigInteger {
    val monkeys = parse(input)
    monkeys.takeTurns(20)
    return monkeys.calculateMonkeyBusiness()
}

fun part2(input: String): BigInteger {
    val monkeys = parse(input)
    val combinedTestFactor = monkeys.calculateCombinedTestFactor()
    monkeys.forEach { it.combinedTestFactor = combinedTestFactor }
    monkeys.takeTurns(10000)
    return monkeys.calculateMonkeyBusiness()
}

private fun List<Monkey>.calculateMonkeyBusiness(): BigInteger {
    return map { it.inspections }.sortedDescending().take(2).let { it[0] * it[1] }
}

private fun List<Monkey>.takeTurns(numberOfTurns: Int) {
    repeat(numberOfTurns) {
        forEach {
            val throws = it.takeTurn()
            applyThrows(throws)
        }
    }
}


data class Monkey(
    val items: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val testFactor: BigInteger,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var combinedTestFactor: BigInteger?,
    var inspections: BigInteger = BigInteger.ZERO
) {
    val test = { it: BigInteger -> it % testFactor == BigInteger.ZERO }

    fun takeTurn(): List<Throw> {
        inspections = inspections.add(items.size.toBigInteger())
        return items.map { item ->
            val newWorryLevel = if (combinedTestFactor != null) operation(item).mod(combinedTestFactor)
            else operation(item) / 3.toBigInteger()

            Throw(
                item = newWorryLevel,
                toMonkey = if (test(newWorryLevel)) trueMonkey else falseMonkey
            )
        }.also { items.clear() }

    }
}

data class Throw(
    val item: BigInteger,
    val toMonkey: Int
)

fun parse(input: String): List<Monkey> {
    return input.split("\n\n")
        .map {
            val lines = it.split("\n")
            val items = lines.getValue("Starting items").split(", ").map { it.trim().toBigInteger() }
            val operation = lines.getValue("Operation").split(" ").takeLast(3)
            val testValue = lines.getValue("Test").split(" ").last().toBigInteger()
            val trueMonkey = lines.getValue("true").split(" ").last().toInt()
            val falseMonkey = lines.getValue("false").split(" ").last().toInt()

            Monkey(
                items = items.toMutableList(),
                operation = getOperationFunction(operation),
                testFactor = testValue,
                trueMonkey = trueMonkey,
                falseMonkey = falseMonkey,
                combinedTestFactor = null
            )
        }
}

private fun List<String>.getValue(key: String) =
    single { it.contains(key) }.split(":").last()

private fun getOperationFunction(operation: List<String>) = { it: BigInteger ->
    val operand1 = if (operation[0] == "old") it else operation[0].toBigInteger()
    val operand2 = if (operation[2] == "old") it else operation[2].toBigInteger()

    when {
        operation[1] == "+" -> operand1 + operand2
        operation[1] == "*" -> operand1 * operand2
        else -> TODO()
    }
}

private fun List<Monkey>.applyThrows(throws: List<Throw>) {
    throws.forEach {
        this[it.toMonkey].items.add(it.item)
    }
}

private fun List<Monkey>.calculateCombinedTestFactor() = this.map { it.testFactor }
    .fold(1.toBigInteger()) { it, acc -> it * acc }