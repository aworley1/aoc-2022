package day4

import util.getInputLines

fun main() {
    println(part1(getInputLines(4)))
    println(part2(getInputLines(4)))
}

fun part1(input: List<String>): Int {
    return input.map { parse(it) }
        .count { oneRangeContainsOtherCompletely(it) }
}

fun part2(input: List<String>): Int {
    return input.map { parse(it) }
        .count { oneRangeContainsOtherPartially(it) }
}

fun oneRangeContainsOtherCompletely(pair: Pair<IntRange, IntRange>): Boolean {
    return with(pair) {
        val firstSet = first.toSet()
        val secondSet = second.toSet()
        val intersection = firstSet.intersect(secondSet)

        intersection.containsAll(firstSet) || intersection.containsAll(secondSet)
    }
}

fun oneRangeContainsOtherPartially(pair: Pair<IntRange, IntRange>): Boolean {
    return with(pair) {
        first.toSet().intersect(second.toSet()).isNotEmpty()
    }
}

fun parse(input: String): Pair<IntRange, IntRange> {
    fun createRange(string: String) = string.split("-").let { it[0].toInt()..it[1].toInt() }
    return input.split(",")
        .let {
            Pair(createRange(it[0]), createRange(it[1]))
        }

}