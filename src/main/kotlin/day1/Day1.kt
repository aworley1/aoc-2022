package day1

import util.getInputText

fun main() {
    println(part1(getInputText(1)))
    println(part2(getInputText(1)))
}

fun part1(input: String): Long {
    val totals = getTotals(input)

    return totals.max()
}

private fun getTotals(input: String) = input.split("\n\n")
    .map { elf ->
        elf.split("\n").sumOf { it.toLong() }
    }

fun part2(input: String): Long {
    return getTotals(input).sortedDescending().take(3).sum()
}