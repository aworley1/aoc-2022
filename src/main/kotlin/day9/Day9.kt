package day9

import day9.Coord.Companion.ZERO
import day9.Direction.Companion.toDirection
import util.getInputLines
import kotlin.math.abs

fun main() {
    println(part1(getInputLines(9)))
    println(part2(getInputLines(9)))
}

fun part1(input: List<String>): Int {
    val instructions = getInstructions(input)
    val initial = Rope.build(1)
    val tailPositions = getLastTailPositions(instructions, initial)

    return tailPositions.size
}

fun part2(input: List<String>): Int {
    val instructions = getInstructions(input)
    val initial = Rope.build(9)
    val tailPositions = getLastTailPositions(instructions, initial)

    return tailPositions.size
}

private fun getLastTailPositions(
    instructions: List<Direction>,
    initialRope: Rope
) = instructions.fold(initialRope to emptySet<Coord>()) { (rope, tailPositions), direction ->
    val newRope = rope.move(direction)
    Pair(newRope, tailPositions + newRope.tails.last)
}.second

private fun getInstructions(input: List<String>): List<Direction> {
    return input.map { it.split(" ") }
        .map { it[0].toDirection() to it[1].toInt() }
        .flatMap { (direction, count) -> List(count) { direction } }
}

data class Rope(
    val head: Coord,
    val tails: Tails
) {
    fun move(direction: Direction): Rope {
        val newHead = head.move(direction)
        val newTails = tails.follow(newHead)

        return Rope(
            head = head.move(direction),
            tails = newTails
        )
    }

    companion object {
        fun build(tailsCount: Int) = Rope(ZERO, Tails.build(tailsCount))
    }
}

data class Tails(val tails: List<Coord>) {
    val last: Coord
        get() = tails.last()

    fun follow(newPosition: Coord): Tails {
        val initial = Pair(newPosition, emptyList<Coord>())
        val newPositions = tails.fold(initial) { acc, tail ->
            val (newPositionOfNextTail, output) = acc
            val newTail = moveTail(tail, newPositionOfNextTail)

            Pair(newTail, output + newTail)
        }.second

        return Tails(newPositions)
    }

    companion object {
        fun build(count: Int) = Tails(List(count) { ZERO })

        private fun moveTail(tail: Coord, newPositionToFollow: Coord): Coord = when {
            tail.isTouching(newPositionToFollow) -> tail
            tail.isInSameRowOrCol(newPositionToFollow) -> tail.moveStraightTowards(newPositionToFollow)
            else -> tail.moveDiagonallyTowards(newPositionToFollow)
        }
    }
}

data class Coord(val row: Int, val col: Int) {
    fun move(direction: Direction): Coord = this + direction.addToMove

    operator fun plus(other: Coord): Coord {
        return Coord(row = this.row + other.row, col = this.col + other.col)
    }

    fun isTouching(other: Coord): Boolean {
        val adjacentOrSame = abs(this.row - other.row) <= 1 && abs(this.col - other.col) <= 1
        val diagonal = abs(this.row - other.row) == 1 && abs(this.col - other.col) == 1
        return adjacentOrSame || diagonal
    }

    fun isInSameRowOrCol(other: Coord): Boolean = this.isInSameCol(other) || this.isInSameRow(other)
    private fun isInSameRow(other: Coord): Boolean = this.row == other.row
    private fun isInSameCol(other: Coord): Boolean = this.col == other.col

    fun moveDiagonallyTowards(other: Coord): Coord {
        if (this.isInSameRowOrCol(other)) throw RuntimeException("Is in the same row or column")

        val move = Coord(
            row = if (this.row > other.row) -1 else 1,
            col = if (this.col > other.col) -1 else 1
        )

        return this + move
    }

    fun moveStraightTowards(other: Coord): Coord {
        val move = Coord(
            col = if (this.isInSameRow(other)) if (this.col > other.col) -1 else 1 else 0,
            row = if (this.isInSameCol(other)) if (this.row > other.row) -1 else 1 else 0
        )

        return this + move

    }

    companion object {
        val ZERO = Coord(0, 0)
    }
}

enum class Direction(val addToMove: Coord) {
    UP(Coord(1, 0)),
    DOWN(Coord(-1, 0)),
    LEFT(Coord(0, -1)),
    RIGHT(Coord(0, 1));

    companion object {
        fun String.toDirection() = when (this) {
            "U" -> UP
            "D" -> DOWN
            "L" -> LEFT
            "R" -> RIGHT
            else -> throw RuntimeException("Impossible!")
        }
    }
}