package day7

import util.getInputLines

typealias FileMap = Map<String, FileOrDir>

fun main() {
    println(part1(getInputLines(7)))
    println(part2(getInputLines(7)))
}

fun part1(input: List<String>): Long {
    val fileMap = createFileMap(parseCommands(input))

    val directoryPaths = fileMap
        .filterValues { it is Directory }
        .map { it.key }

    val directoriesWithSizes = directoryPaths.map {
        it to fileMap.sumFilesForDirectory(it)
    }.toMap()

    return directoriesWithSizes.filterValues { it <= 100000 }.values.sum()
}

fun part2(input: List<String>): Long {
    val fileMap = createFileMap(parseCommands(input))

    val directoryPaths = fileMap
        .filterValues { it is Directory }
        .map { it.key }

    val directoriesWithSizes = directoryPaths.map {
        it to fileMap.sumFilesForDirectory(it)
    }.toMap()

    val usedSpace = directoriesWithSizes["/"]!!
    val freeSpace = 70000000 - usedSpace
    val requiredToFree = 30000000 - freeSpace

    return directoriesWithSizes.filterValues { it >= requiredToFree }.minBy { it.value }.value

}

fun FileMap.sumFilesForDirectory(path: String) = this.filter { (k, v) ->
    k.startsWith(path)
}.values.filterIsInstance<File>().sumOf { it.size }

fun parseCommands(input: List<String>): List<Command> {
    return input.fold(emptyList()) { acc, line ->
        val split = line.split(" ")
        if (line.startsWith('$'))
            acc + Command(split[1], split.getOrNull(2))
        else acc.dropLast(1) + acc.last().copy(output = acc.last().output + line)
    }
}

fun createFileMap(commands: List<Command>): FileMap {
    var currentDirectory = ""
    val output = mutableMapOf<String, FileOrDir>("/" to Directory(""))

    commands.drop(1).forEach {
        when (it.command) {
            "cd" -> when (val argument = it.argument!!) {
                ".." -> currentDirectory = currentDirectory.split("/").dropLast(1).joinToString("/")
                else -> currentDirectory += "/${argument}"
            }

            "ls" -> it.output.forEach {
                val fileOrDir = it.toFileOrDir()
                output.put("$currentDirectory/${fileOrDir.name}", fileOrDir)
            }
        }
    }

    return output
}

sealed class FileOrDir(open val name: String)
data class File(
    override val name: String,
    val size: Long
) : FileOrDir(name)

data class Directory(
    override val name: String,
) : FileOrDir(name)

data class Command(val command: String, val argument: String? = null, val output: List<String> = emptyList())

fun String.toFileOrDir() = if (startsWith("dir")) Directory(split(" ").last())
else File(split(" ").last(), split(" ").first().toLong())