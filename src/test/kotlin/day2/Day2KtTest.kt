package day2

import day2.Outcome.*
import day2.Shape.PAPER
import day2.Shape.ROCK
import day2.Shape.SCISSORS
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import util.getSampleInputLines

internal class Day2KtTest {

    @Nested
    inner class Part1 {
        @Test
        fun `rock should beat scissors`() {
            val row = Row(opponent = SCISSORS, you = ROCK)
            assertThat(winScore(row)).isEqualTo(6)
        }

        @Test
        fun `scissors should beat paper`() {
            val row = Row(opponent = PAPER, you = SCISSORS)
            assertThat(winScore(row)).isEqualTo(6)
        }

        @Test
        fun `paper should beat rock`() {
            val row = Row(opponent = ROCK, you = PAPER)
            assertThat(winScore(row)).isEqualTo(6)
        }

        @Test
        fun `scissors should draw with scissors`() {
            val row = Row(opponent = SCISSORS, you = SCISSORS)
            assertThat(winScore(row)).isEqualTo(3)
        }

        @Test
        fun `paper should draw with paper`() {
            val row = Row(opponent = PAPER, you = PAPER)
            assertThat(winScore(row)).isEqualTo(3)
        }

        @Test
        fun `rock should draw with rock`() {
            val row = Row(opponent = ROCK, you = ROCK)
            assertThat(winScore(row)).isEqualTo(3)
        }

        @Test
        fun `scissors should lose to rock`() {
            val row = Row(opponent = ROCK, you = SCISSORS)
            assertThat(winScore(row)).isEqualTo(0)
        }

        @Test
        fun `paper should lose to scissors`() {
            val row = Row(opponent = SCISSORS, you = PAPER)
            assertThat(winScore(row)).isEqualTo(0)
        }

        @Test
        fun `rock should lose to paper`() {
            val row = Row(opponent = PAPER, you = ROCK)
            assertThat(winScore(row)).isEqualTo(0)
        }

        @Test
        fun `should correctly match sample input`() {
            assertThat(part1(getSampleInputLines(2, 1))).isEqualTo(15)
        }
    }

    @Nested
    inner class Part2 {
        @Test
        fun `draw should return the same shape`() {
            assertThat(findRequiredShape(ROCK, DRAW)).isEqualTo(ROCK)
            assertThat(findRequiredShape(PAPER, DRAW)).isEqualTo(PAPER)
            assertThat(findRequiredShape(SCISSORS, DRAW)).isEqualTo(SCISSORS)
        }

        @Test
        fun `win should return the winning shape`() {
            assertThat(findRequiredShape(ROCK, WIN)).isEqualTo(PAPER)
            assertThat(findRequiredShape(PAPER, WIN)).isEqualTo(SCISSORS)
            assertThat(findRequiredShape(SCISSORS, WIN)).isEqualTo(ROCK)
        }

        @Test
        fun `lose should return the losing shape`() {
            assertThat(findRequiredShape(ROCK, LOSE)).isEqualTo(SCISSORS)
            assertThat(findRequiredShape(PAPER, LOSE)).isEqualTo(ROCK)
            assertThat(findRequiredShape(SCISSORS, LOSE)).isEqualTo(PAPER)
        }

        @Test
        fun `should correctly match sample input`() {
            assertThat(part2(getSampleInputLines(2, 1))).isEqualTo(12)
        }
    }
}