package day2

import day2.Outcome.DRAW
import day2.Outcome.LOSE
import day2.Outcome.WIN
import day2.Shape.PAPER
import day2.Shape.ROCK
import day2.Shape.SCISSORS
import util.getInputLines


fun main() {
    println(part1(getInputLines(2)))
    println(part2(getInputLines(2)))
}

fun part1(input: List<String>): Int {
    return input.map { parsePart1(it) }.sumOf { winScore(it) + it.you.score }
}

fun part2(input: List<String>): Int {
    return input
        .map { parsePart2(it) }
        .map {
            Row(
                opponent = it.opponent,
                you = findRequiredShape(it.opponent, it.outcome)
            )
        }
        .sumOf { winScore(it) + it.you.score }
}

fun parsePart1(line: String) = line.split(" ").let {
    Row(
        you = Shape.from(it[1]),
        opponent = Shape.from(it[0])
    )
}

fun parsePart2(line: String) = line.split(" ").let {
    Part2Row(
        opponent = Shape.from(it[0]),
        outcome = Outcome.from(it[1])
    )
}

fun findRequiredShape(shape: Shape, outcome: Outcome): Shape {
    return when (outcome) {
        DRAW -> shape
        WIN -> when (shape) {
            SCISSORS -> ROCK
            ROCK -> PAPER
            PAPER -> SCISSORS
        }

        LOSE -> when (shape) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }
    }
}

fun winScore(row: Row): Int {
    return when {
        row.you.beats(row.opponent) -> 6
        row.you == row.opponent -> 3
        else -> 0
    }
}

enum class Shape(val part1Letters: List<String>, val score: Int) {
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
        fun from(letter: String) = values().single { it.part1Letters.contains(letter) }
    }
}

enum class Outcome(val letter: String) {
    WIN("Z"),
    LOSE("X"),
    DRAW("Y");

    companion object {
        fun from(letter: String) = values().single { it.letter == letter }
    }
}

data class Row(
    val you: Shape,
    val opponent: Shape
)

data class Part2Row(
    val opponent: Shape,
    val outcome: Outcome
)