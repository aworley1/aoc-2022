package day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.getSampleInputLines

internal class Day7KtTest {
    @Test
    fun `should parse commands`() {
        val input = getSampleInputLines(7, 1).take(7)
        assertThat(parseCommands(input)).containsExactly(
            Command("cd", "/"),
            Command(command = "ls", output = sampleFirstLsOutput),
            Command("cd", "a"),
        )
    }

}

private val sampleFirstLsOutput = listOf("dir a", "14848514 b.txt", "8504156 c.dat", "dir d")