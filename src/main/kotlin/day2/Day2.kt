package day2

import util.getInputLines


fun main() {
    println(part1(getInputLines(2)))
}

fun part1(input: List<String>): Int {
    return input.map { parse(it) }.sumOf { winScore(it) + it.you.score }
}

fun parse(line: String) = line.split(" ").let {
    Row(
        you = Shape.from(it[1]),
        opponent = Shape.from(it[0])
    )
}

fun winScore(row: Row): Int {
    return when {
        row.you.beats(row.opponent) -> 6
        row.you == row.opponent -> 3
        else -> 0
    }
}

enum class Shape(val letters: List<String>, val score: Int) {
    ROCK(listOf("A", "X"), 1),
    PAPER(listOf("B", "Y"), 2),
    SCISSORS(listOf("C", "Z"), 3);

    fun beats(shape: Shape) = when {
        this == ROCK && shape == SCISSORS -> true
        this == PAPER && shape == ROCK -> true
        this == SCISSORS && shape == PAPER -> true
        else -> false
    }

    companion object {
        fun from(letter: String) = values().single { it.letters.contains(letter) }
    }
}

data class Row(
    val you: Shape,
    val opponent: Shape
)