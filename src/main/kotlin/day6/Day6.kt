package day6

import util.getInputText

fun main() {
    println(part1(getInputText(6)))
    println(part2(getInputText(6)))
}

fun part1(input: String) = input.findFirstDistinct(4)
fun part2(input: String) = input.findFirstDistinct(14)

private fun String.findFirstDistinct(charCount: Int): Int {
    val firstDistinctChars = windowed(charCount)
        .first { it.length == it.toSet().size }

    return indexOf(firstDistinctChars) + charCount
}