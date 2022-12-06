package day6

import util.getInputText

fun main() {
    println(part1(getInputText(6)))
}

fun part1(input: String): Int {
    val firstDistinctChars = input.windowed(4).first { it.length == it.toCharArray().distinct().size }

    return input.indexOf(firstDistinctChars) + 4
}