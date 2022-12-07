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

    @Test
    fun `should match sample tree`() {
        assertThat(createFileMap(parseCommands(getSampleInputLines(7, 1)))).containsExactlyInAnyOrderEntriesOf(
            mapOf(
                ("/" to Directory("")),
                ("/a" to Directory("a")),
                ("/a/e" to Directory("e")),
                ("/a/e/i" to File("i", 584)),
                ("/a/f" to File("f", 29116)),
                ("/a/g" to File("g", 2557)),
                ("/a/h.lst" to File("h.lst", 62596)),
                ("/b.txt" to File("b.txt", 14848514)),
                ("/c.dat" to File("c.dat", 8504156)),
                ("/d" to Directory("d")),
                ("/d/j" to File("j", 4060174)),
                ("/d/d.log" to File("d.log", 8033020)),
                ("/d/d.ext" to File("d.ext", 5626152)),
                ("/d/k" to File("k", 7214296)),
            )
        )
    }

    @Test
    fun `should match part 1 sample output`() {
        assertThat(part1(getSampleInputLines(7, 1))).isEqualTo(95437)
    }
}

private val sampleFirstLsOutput = listOf("dir a", "14848514 b.txt", "8504156 c.dat", "dir d")