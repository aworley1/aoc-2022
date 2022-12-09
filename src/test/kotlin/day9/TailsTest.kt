package day9

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TailsTest {
    @Test
    fun `tails should not follow head when still touching`() {
        val initial = Tails.build(2)

        val result = initial.follow(Coord(0,1))

        assertThat(result.tails).containsExactly(Coord.ZERO, Coord.ZERO)
    }

    @Test
    fun `first tail should follow head`() {
        val initial = Tails.build(2)

        val result = initial.follow(Coord(0,2))

        assertThat(result.tails).containsExactly(Coord(0,1), Coord.ZERO)
    }

    @Test
    fun `second tail should follow first tail`() {
        val initial = Tails(listOf(Coord(0,1), Coord.ZERO))

        val result = initial.follow(Coord(0,3))

        assertThat(result.tails).containsExactly(Coord(0,2), Coord(0,1))
    }
}