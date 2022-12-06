package day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day6KtTest {
    @Test
    fun `should match part 1 examples`() {
        assertThat(part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb")).isEqualTo(7)
        assertThat(part1("bvwbjplbgvbhsrlpgdmjqwftvncz")).isEqualTo(5)
        assertThat(part1("nppdvjthqldpwncqszvftbrmjlhg")).isEqualTo(6)
        assertThat(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")).isEqualTo(10)
        assertThat(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")).isEqualTo(11)
    }
}