package day9

import day9.Direction.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.getSampleInputLines

internal class Day9KtTest {
    @Test
    fun `should move`() {
        val initial = Coord(3, 5)

        assertThat(initial.move(UP)).isEqualTo(Coord(4, 5))
        assertThat(initial.move(DOWN)).isEqualTo(Coord(2, 5))
        assertThat(initial.move(LEFT)).isEqualTo(Coord(3, 4))
        assertThat(initial.move(RIGHT)).isEqualTo(Coord(3, 6))
    }

    @Test
    fun `should check if two coords are touching`() {
        val initial = Coord(3, 5)

        assertThat(initial.isTouching(Coord(3, 5))).isTrue()

        assertThat(initial.isTouching(Coord(2, 4))).isTrue()
        assertThat(initial.isTouching(Coord(3, 4))).isTrue()
        assertThat(initial.isTouching(Coord(4, 4))).isTrue()

        assertThat(initial.isTouching(Coord(2, 5))).isTrue()
        assertThat(initial.isTouching(Coord(4, 5))).isTrue()

        assertThat(initial.isTouching(Coord(2, 6))).isTrue()
        assertThat(initial.isTouching(Coord(3, 6))).isTrue()
        assertThat(initial.isTouching(Coord(4, 6))).isTrue()

        assertThat(initial.isTouching(Coord(5, 6))).isFalse()
    }

    @Test
    fun `should move 1 step diagonally towards`() {
        val other = Coord(2, 2)
        val testCoord = Coord.ZERO

        assertThat(testCoord.moveDiagonallyTowards(other)).isEqualTo(Coord(1, 1))
    }

    @Test
    fun `should move 1 step up towards`() {
        val other = Coord(2, 0)
        val testCoord = Coord.ZERO

        assertThat(testCoord.moveStraightTowards(other)).isEqualTo(Coord(1, 0))
    }

    @Test
    fun `should move 1 step down towards`() {
        val other = Coord.ZERO
        val testCoord = Coord(2, 0)

        assertThat(testCoord.moveStraightTowards(other)).isEqualTo(Coord(1, 0))
    }

    @Test
    fun `should move 1 step left towards`() {
        val other = Coord.ZERO
        val testCoord = Coord(0, 2)

        assertThat(testCoord.moveStraightTowards(other)).isEqualTo(Coord(0, 1))
    }

    @Test
    fun `should move 1 step right towards`() {
        val other = Coord(0, 2)
        val testCoord = Coord.ZERO

        assertThat(testCoord.moveStraightTowards(other)).isEqualTo(Coord(0, 1))
    }

    @Test
    fun `should move rope from initial position`() {
        val inital = Rope.build(1)
        assertThat(inital.move(RIGHT)).isEqualTo(Rope(Coord(0, 1), Tails(listOf(Coord(0, 0)))))
    }

    @Test
    fun `should move rope in the same direction as the tail is lagging`() {
        val inital = Rope(head = Coord(1, 0), tails = Tails(listOf(Coord(0, 0))))
        assertThat(inital.move(UP)).isEqualTo(Rope(Coord(2, 0), Tails(listOf(Coord(1, 0)))))
    }

    @Test
    fun `should move rope diagonally if needed`() {
        val initial = Rope(
            head = Coord(1, 1),
            tails = Tails(listOf(Coord(0, 0)))
        )

        assertThat(initial.move(UP)).isEqualTo(
            Rope(
                head = Coord(row = 2, col = 1),
                tails = Tails(listOf(Coord(row = 1, col = 1)))
            )
        )
    }

    @Test
    fun `should match example part 2 broken`() {
        val startRope = Rope(
            head = Coord(row = 20, col = 20),
            tails = Tails(
                listOf(
                    Coord(row = 19, col = 20),
                    Coord(row = 18, col = 20),
                    Coord(row = 17, col = 20),
                    Coord(row = 16, col = 20),
                    Coord(row = 16, col = 19),
                    Coord(row = 15, col = 18),
                    Coord(row = 14, col = 17),
                    Coord(row = 13, col = 16),
                    Coord(row = 12, col = 15),
                )
            )
        )

        val result = startRope.move(LEFT).move(LEFT).move(LEFT).move(LEFT).move(LEFT).move(LEFT).move(LEFT).move(LEFT)
        val expected = Rope(
            head = Coord(row = 20, col = 12),
            tails = Tails(
                listOf(
                    Coord(row = 20, col = 13),
                    Coord(row = 20, col = 14),
                    Coord(row = 20, col = 15),
                    Coord(row = 20, col = 16),
                    Coord(row = 19, col = 16),
                    Coord(row = 18, col = 16),
                    Coord(row = 17, col = 16),
                    Coord(row = 16, col = 16),
                    Coord(row = 15, col = 16),
                )
            )
        )

//        printRope(result)
//        printRope(expected)

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `should match part 1 input`() {
        assertThat(part1(getSampleInputLines(9, 1))).isEqualTo(13)
    }

    @Test
    fun `should match part 2 input`() {
        assertThat(part2(getSampleInputLines(9, 2))).isEqualTo(36)
    }
}