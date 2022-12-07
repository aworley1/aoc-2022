package day7

import util.getInputLines

typealias FileMap = Map<String, FileOrDir>

fun main() {
    println(part1(getInputLines(7)))
    println(part2(getInputLines(7)))
}

fun part1(input: List<String>): Long {
    val fileMap = createFileMap(parseCommands(input))
    val directoryPaths = getDirectoryPaths(fileMap)
    val directoriesWithSizes = getDirectoriesWithSizes(directoryPaths, fileMap)

    return directoriesWithSizes.filterValues { it <= 100000 }.values.sum()
}

fun part2(input: List<String>): Long {
    val fileMap = createFileMap(parseCommands(input))
    val directoryPaths = getDirectoryPaths(fileMap)
    val directoriesWithSizes = getDirectoriesWithSizes(directoryPaths, fileMap)

    val usedSpace = directoriesWithSizes["/"]!!
    val freeSpace = 70000000 - usedSpace
    val requiredToFree = 30000000 - freeSpace

    return directoriesWithSizes.filterValues { it >= requiredToFree }.minBy { it.value }.value
}

fun parseCommands(input: List<String>): List<Command> {
    return input.fold(emptyList()) { acc, line ->
        val split = line.split(" ")

        if (line.startsWith('$'))
            acc + Command(split[1], split.getOrNull(2))
        else acc.dropLast(1) + acc.last().copy(output = acc.last().output + line)
    }
}

fun createFileMap(commands: List<Command>): FileMap {
    val initial = CurrentDirectoryAndFileMap(
        cd = "",
        fileMap = mapOf<String, FileOrDir>("/" to Directory(""))
    )

    return commands.drop(1).fold(initial) { acc, command ->
        when (command.command) {
            "cd" -> when (val argument = command.argument!!) {
                ".." -> acc.updateCurrentDirectory(acc.cd.removeLastPathElement())
                else -> acc.updateCurrentDirectory(acc.cd + "/${argument}")
            }

            "ls" -> command.output.map {
                it.toFileOrDir().let { fileOrDir -> "${acc.cd}/${fileOrDir.name}" to fileOrDir }
            }.let { acc.addFiles(it) }

            else -> throw RuntimeException("Impossible!")
        }
    }.fileMap
}

private fun getDirectoriesWithSizes(directoryPaths: List<String>, fileMap: FileMap) =
    directoryPaths.associateWith { fileMap.sumFilesForDirectory(it) }

private fun getDirectoryPaths(fileMap: FileMap) = fileMap
    .filterValues { it is Directory }
    .map { it.key }

private fun FileMap.sumFilesForDirectory(path: String) = this.filter { (k, v) ->
    k.startsWith(path)
}.values.filterIsInstance<File>().sumOf { it.size }

data class Command(val command: String, val argument: String? = null, val output: List<String> = emptyList())

sealed class FileOrDir(open val name: String)
data class File(override val name: String, val size: Long) : FileOrDir(name)
data class Directory(override val name: String) : FileOrDir(name)

fun String.toFileOrDir(): FileOrDir {
    return if (startsWith("dir")) Directory(split(" ").last())
    else File(split(" ").last(), split(" ").first().toLong())
}

data class CurrentDirectoryAndFileMap(
    val cd: String,
    val fileMap: FileMap
) {
    fun updateCurrentDirectory(name: String) = this.copy(cd = name)
    fun addFiles(files: List<Pair<String, FileOrDir>>) = this.copy(fileMap = this.fileMap + files.toMap())
}

fun String.removeLastPathElement() = split("/").dropLast(1).joinToString("/")