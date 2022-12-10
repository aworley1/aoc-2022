package day10

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.getSampleInputLines

internal class Day10KtTest {
    @Test
    fun `should match sample 1 input`() {
        assertThat(part1(getSampleInputLines(10,1))).isEqualTo(13140)
    }

}