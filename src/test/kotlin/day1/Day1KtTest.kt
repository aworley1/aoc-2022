package day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.getSampleInputText

internal class Day1KtTest {
    @Test
    fun `should match part1 sample`() {
        val result = part1(getSampleInputText(1, 1))

        assertThat(result).isEqualTo(24000)
    }

    @Test
    fun `should match part2 sample`() {
        val result = part2(getSampleInputText(1, 1))

        assertThat(result).isEqualTo(45000)
    }
}