package day5

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.getSampleInputText

internal class Day5KtTest {
    @Test
    fun `should parse sample stacks`() {
        val input = getSampleInputText(5, 1).split("\n\n").first()

        assertThat(parseStacks(input)).isEqualTo(
            mapOf(
                "1" to listOf("Z", "N"),
                "2" to listOf("M", "C", "D"),
                "3" to listOf("P"),
            )
        )
    }

    @Test
    fun `should match sample 1`() {
        assertThat(part1(getSampleInputText(5, 1))).isEqualTo("CMZ")
    }

    @Test
    fun `should match sample 2`() {
        assertThat(part2(getSampleInputText(5, 1))).isEqualTo("MCD")
    }
}