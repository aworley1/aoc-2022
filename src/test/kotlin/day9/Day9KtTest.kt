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
    fun `should move rope from initial position`() {
        val inital = Rope(head = Coord(0, 0), tail = Coord(0, 0))
        assertThat(inital.move(RIGHT)).isEqualTo(Rope(Coord(0, 1), Coord(0, 0)))
    }

    @Test
    fun `should move rope in the same direction as the tail is lagging`() {
        val inital = Rope(head = Coord(1, 0), tail = Coord(0, 0))
        assertThat(inital.move(UP)).isEqualTo(Rope(Coord(2, 0), Coord(1, 0)))
    }

    @Test
    fun `should move rope diagonally if needed`() {
        val initial = Rope(
            head = Coord(1,1),
            tail = Coord(0,0),
        )

        assertThat(initial.move(UP)).isEqualTo(
            Rope(
                head = Coord(row = 2, col = 1 ),
                tail = Coord(row = 1, col = 1)
            )
        )
    }

    @Test
    fun `should match part 1 input`() {
        assertThat(part1(getSampleInputLines(9,1))).isEqualTo(13)
    }
}