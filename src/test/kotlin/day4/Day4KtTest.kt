package day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.getSampleInputLines

internal class Day4KtTest {
    @Test
    fun `should parse input`() {
        val result = parse("2-4,6-8")

        assertThat(result.first).containsExactly(2,3,4)
        assertThat(result.second).containsExactly(6,7,8)
    }

    @Test
    fun `should match sample input part 1`() {
        assertThat(part1(getSampleInputLines(4, 1))).isEqualTo(2)
    }

    @Test
    fun `should decide if one range contains the other completely`() {
        assertThat(oneRangeContainsOtherCompletely(Pair(1..10, 2..5))).isTrue()
        assertThat(oneRangeContainsOtherCompletely(Pair(2..5, 1..10))).isTrue()
        assertThat(oneRangeContainsOtherCompletely(Pair(2..5, 3..7))).isFalse()
    }

    @Test
    fun `should decide if one range contains the other at all`() {
        assertThat(oneRangeContainsOtherPartially(Pair(1..10, 9..11))).isTrue()
        assertThat(oneRangeContainsOtherPartially(Pair(9..11, 1..10))).isTrue()
        assertThat(oneRangeContainsOtherPartially(Pair(1..2, 3..4))).isFalse()
        assertThat(oneRangeContainsOtherPartially(Pair(3..4, 1..2))).isFalse()
    }

    @Test
    fun `should match sample input part 2`() {
        assertThat(part2(getSampleInputLines(4, 1))).isEqualTo(4)
    }
}