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

    @Test
    fun `should match part 2 examples`() {
        assertThat(part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb")).isEqualTo(19)
        assertThat(part2("bvwbjplbgvbhsrlpgdmjqwftvncz")).isEqualTo(23)
        assertThat(part2("nppdvjthqldpwncqszvftbrmjlhg")).isEqualTo(23)
        assertThat(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")).isEqualTo(29)
        assertThat(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")).isEqualTo(26)
    }
}