package day7

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
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

    @Test
    fun `should convert commands to a file map`() {
        val commands = listOf(
            Command("cd", "/"),
            Command(command = "ls", output = sampleFirstLsOutput),
        )

        assertThat(createFileMap(commands)).containsExactly(
            entry("/", Directory("")),
            entry("/a", Directory("a")),
            entry("/b.txt", File("b.txt", 14848514)),
            entry("/c.dat", File("c.dat", 8504156)),
            entry("/d", Directory("d")),
        )
    }

}

private val sampleFirstLsOutput = listOf("dir a", "14848514 b.txt", "8504156 c.dat", "dir d")