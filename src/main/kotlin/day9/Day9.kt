package day9

import day9.Coord.Companion.ZERO
import day9.Direction.Companion.toDirection
import util.getInputLines
import kotlin.math.abs

fun main() {
    println(part1(getInputLines(9)))
}

fun part1(input: List<String>): Int {
    val instructions = input.map { it.split(" ") }
        .map { it[0].toDirection() to it[1].toInt() }
        .flatMap { (direction, count) -> List(count) { direction } }

    val initial = Pair(Rope.build(1), emptySet<Coord>())

    val tailPositions = instructions.fold(initial) { (rope, tailPositions), direction ->
        val newRope = rope.move(direction)
        Pair(newRope, tailPositions + newRope.tails.last)
    }.second

    return tailPositions.size
}

data class Rope(
    val head: Coord,
    val tails: Tails
) {
    fun move(direction: Direction): Rope {
        val newHead = head.move(direction)
        val newTails = tails.followHead(newHead, head, direction)

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

    fun followHead(newHead: Coord, oldHead: Coord, direction: Direction) =
        Tails(listOf(moveTail(newHead, oldHead, direction)))

    private fun moveTail(newHead: Coord, oldHead: Coord, direction: Direction): Coord = when {
        last.isTouching(newHead) -> last
        newHead.isInSameRowOrCol(last) -> last.move(direction)
        else -> oldHead
    }

    companion object {
        fun build(count: Int) = Tails(List(count) { ZERO })
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
    fun isInSameRow(other: Coord): Boolean = this.row == other.row
    fun isInSameCol(other: Coord): Boolean = this.col == other.col

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