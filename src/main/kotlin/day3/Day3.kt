package day3

import util.getInputLines

fun main() {
    println(part1(getInputLines(3)))
}

fun part1(input: List<String>): Int {
    return input.map { getCompartments(it) }
        .map { findCommonItemTypes(it).sumOf { getPriority(it) } }
        .sum()
}

fun getCompartments(rucksack: String): Pair<String, String> {
    if (rucksack.length % 2 != 0) throw RuntimeException("Non even rucksack")
    val halfLength = rucksack.length / 2
    return Pair(rucksack.substring(0, halfLength), rucksack.substring(halfLength))
}

fun findCommonItemTypes(rucksack: Pair<String, String>): Set<String> {
    val firstCompartment = rucksack.first.map { it.toString() }
    val secondCompartment = rucksack.second.map { it.toString() }

    return firstCompartment
        .intersect(secondCompartment)
        .toSet()
}

fun getPriority(type: String): Int {
    val priorities = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    return priorities.indexOf(type.toCharArray().single()) + 1
}