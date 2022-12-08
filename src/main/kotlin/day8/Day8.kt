package day8

import util.getInputLines

fun main() {
    println(part1(getInputLines(8)))
    println(part2(getInputLines(8)))
}

fun part1(input: List<String>): Int {
    val trees = parse(input)

    val rows = trees.map { it.row }.distinct()
    val cols = trees.map { it.col }.distinct()

    val visibleTrees = (rows.flatMap {
        getVisibleTrees(trees, it, false)
    } + rows.flatMap {
        getVisibleTrees(trees, it, true)
    } + cols.flatMap {
        getVisibleTreesCol(trees, it, false)
    } + cols.flatMap {
        getVisibleTreesCol(trees, it, true)
    }).toSet()

    return visibleTrees.size
}

fun part2(input: List<String>): Int {
    val trees = parse(input)

    val treesWithTheirViews = parse(input).map {
        it to listOf(
            getViewingDistanceDown(trees, it, false),
            getViewingDistanceDown(trees, it, true),
            getViewingDistanceRight(trees, it, false),
            getViewingDistanceRight(trees, it, true)
        )
    }

    val treesWithScenicIndex = treesWithTheirViews
        .map { it.first to it.second[0].size * it.second[1].size* it.second[2].size* it.second[3].size  }

    return treesWithScenicIndex.maxOf { it.second }
}

fun getViewingDistanceDown(trees: List<Tree>, tree: Tree, reversed: Boolean): List<Tree> {
    val col = trees
        .filter { it.col == tree.col }
        .sortedBy { it.row }
        .filter { if (reversed) it.row < tree.row else it.row > tree.row }
        .let { if (reversed) it.reversed() else it }

    val lastVisibleTree = col.firstOrNull { it.height >= tree.height }

    val visibleTrees = lastVisibleTree?.let { col.subList(0, col.indexOf(it) + 1) } ?: col

    return visibleTrees
}

fun getViewingDistanceRight(trees: List<Tree>, tree: Tree, reversed: Boolean): List<Tree> {
    val row = trees
        .filter { it.row == tree.row }
        .sortedBy { it.col }
        .filter { if (reversed) it.col < tree.col else it.col > tree.col }
        .let { if (reversed) it.reversed() else it }

    val lastVisibleTree = row.firstOrNull { it.height >= tree.height }

    val visibleTrees = lastVisibleTree?.let { row.subList(0, row.indexOf(it) + 1) } ?: row

    return visibleTrees
}


fun getVisibleTrees(trees: List<Tree>, rowNum: Int, reversed: Boolean): List<Tree> {
    val row = trees
        .filter { it.row == rowNum }
        .sortedBy { it.col }
        .let { if (reversed) it.reversed() else it }

    val visibleTrees = mutableSetOf<Tree>()
    var maxHeightSoFar = -1

    row.forEach {
        if (it.height > maxHeightSoFar) {
            maxHeightSoFar = it.height
            visibleTrees.add(it)
        }
    }

    return visibleTrees.toList()
}

fun getVisibleTreesCol(trees: List<Tree>, colNum: Int, reversed: Boolean): Set<Tree> {
    val col = trees
        .filter { it.col == colNum }
        .sortedBy { it.row }
        .let { if (reversed) it.reversed() else it }

    val visibleTrees = mutableSetOf<Tree>()
    var maxHeightSoFar = -1

    col.forEach {
        if (it.height > maxHeightSoFar) {
            maxHeightSoFar = it.height
            visibleTrees.add(it)
        }
    }

    return visibleTrees
}

fun parse(input: List<String>): List<Tree> {
    return input.mapIndexed { row, cols ->
        cols.mapIndexed { col, height ->
            height.toString().toInt().let { Tree(row, col, it) }
        }
    }.flatten()
}

data class Tree(
    val row: Int,
    val col: Int,
    val height: Int
)