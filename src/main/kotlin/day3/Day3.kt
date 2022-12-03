package day3

import util.getInputLines

fun main() {
    println(part1(getInputLines(3)))
    println(part2(getInputLines(3)))
}

fun part1(input: List<String>): Int {
    return input.map { getCompartments(it) }
        .map { findCommonItemTypes(it).sumOf { getPriority(it) } }
        .sum()
}
fun part2(input: List<String>): Int {
    return input.chunked(3)
        .map { findCommonItemType(it) }
        .sumOf { getPriority(it) }
}

fun getCompartments(rucksack: String): Pair<String, String> {
    if (rucksack.length % 2 != 0) throw RuntimeException("Non even rucksack")
    val halfLength = rucksack.length / 2
    return Pair(rucksack.substring(0, halfLength), rucksack.substring(halfLength))
}

fun findCommonItemTypes(rucksack: Pair<String, String>): Set<String> {
    val firstCompartment = rucksack.first.toChars()
    val secondCompartment = rucksack.second.toChars()

    return firstCompartment
        .intersect(secondCompartment)
        .toSet()
}

fun findCommonItemType(rucksacks: List<String>): String {
    if (rucksacks.size != 3) throw RuntimeException("Too many rucksacks")

    return (rucksacks[0].toChars()).intersect(rucksacks[1].toChars()).intersect(rucksacks[2].toChars()).single()
}

private fun String.toChars() = map { it.toString() }

fun getPriority(type: String): Int {
    val priorities = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    return priorities.indexOf(type.toCharArray().single()) + 1
}