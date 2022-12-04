package day4

import util.getInputLines

fun main() {
    println(part1(getInputLines(4)))
}

fun part1(input: List<String>): Int {
    return input.map { parse(it) }
        .count { oneRangeContainsOther(it) }
}

fun oneRangeContainsOther(pair: Pair<IntRange, IntRange>): Boolean {
    return when {
        pair.first.first <= pair.second.first && pair.first.last >= pair.second.last ||
        pair.second.first <= pair.first.first && pair.second.last >= pair.first.last -> true
        else -> false
    }
}

fun parse(input: String): Pair<IntRange, IntRange> {
    fun createRange(string: String) = string.split("-").let { it[0].toInt()..it[1].toInt() }
    return input.split(",")
        .let {
            Pair(createRange(it[0]), createRange(it[1]))
        }

}