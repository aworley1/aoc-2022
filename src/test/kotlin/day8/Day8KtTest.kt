package day8

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import util.getSampleInputLines

internal class Day8KtTest {
    @Test
    fun `should parse`() {
        val input = listOf("12","34")

        assertThat(parse(input)).containsExactlyInAnyOrder(
            Tree(0,0, 1), Tree(0,1,2),
            Tree(1,0, 3), Tree(1,1,4),
        )
    }

    @Test
    fun `should count visible trees in a row, forwards`() {
        val trees = listOf(
            Tree(0,0, 1),
            Tree(0,1,2),
            Tree(0,2,2),
            Tree(0,3,3),
            Tree(0,3,1),
        )

        assertThat(getVisibleTrees(trees, 0, false)).containsExactlyInAnyOrder(
            Tree(0,0, 1),
            Tree(0,1,2),
            Tree(0,3,3),
        )
    }

    @Test
    fun `should count visible trees in a row, backwards`() {
        val trees = listOf(
            Tree(0,0, 1),
            Tree(0,1,2),
            Tree(0,2,2),
            Tree(0,3,3),
            Tree(0,3,1),
        )

        assertThat(getVisibleTrees(trees, 0, true)).containsExactlyInAnyOrder(
            Tree(0,3,3),
            Tree(0,3,1),
        )
    }

    @Test
    fun `should count visible trees in a column, backwards`() {
        val trees = listOf(
            Tree(0,0, 1),
            Tree(1,0,2),
            Tree(2,0,2),
            Tree(3,0,3),
            Tree(4,0,1),
        )

        assertThat(getVisibleTreesCol(trees, 0, true)).containsExactlyInAnyOrder(
            Tree(3,0,3),
            Tree(4,0,1),
        )
    }

    @Test
    fun `should count visible trees in a column, forwards`() {
        val trees = listOf(
            Tree(0,0, 1),
            Tree(1,0,2),
            Tree(2,0,2),
            Tree(3,0,3),
            Tree(4,0,1),
        )

        assertThat(getVisibleTreesCol(trees, 0, false)).containsExactlyInAnyOrder(
            Tree(0,0, 1),
            Tree(1,0,2),
            Tree(3,0,3),
        )
    }

    @Test
    fun `should match part 1 sample`() {
        assertThat(part1(getSampleInputLines(8,1))).isEqualTo(21)
    }
}