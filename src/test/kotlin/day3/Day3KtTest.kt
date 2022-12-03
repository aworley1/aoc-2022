package day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.getSampleInputLines

internal class Day3KtTest {
    @Test
    fun `should split rucksack into two compartments`() {
        val rucksack = "abcdefgh"
        val result = getCompartments(rucksack)
        assertThat(result.first).isEqualTo("abcd")
        assertThat(result.second).isEqualTo("efgh")
    }

    @Test
    fun `should find common items`() {
        val rucksack = Pair("aJbcF", "FkiuuJ")

        assertThat(findCommonItemTypes(rucksack)).containsExactlyInAnyOrder("J", "F")
    }

    @Test
    fun `should find priorities`() {
        assertThat(getPriority("p")).isEqualTo(16)
        assertThat(getPriority("P")).isEqualTo(42)
    }

    @Test
    fun `should match part 1 sample`() {
        assertThat(part1(getSampleInputLines(3,1))).isEqualTo(157)
    }

    @Test
    fun `should match part 2 sample`() {
        assertThat(part2(getSampleInputLines(3, 2))).isEqualTo(70)
    }
}