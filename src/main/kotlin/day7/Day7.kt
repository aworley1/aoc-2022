package day7

import util.getSampleInputLines

typealias FileMap = Map<String, FileOrDir>

fun main() {
    parseCommands(getSampleInputLines(7, 1))
}

fun parseCommands(input: List<String>): List<Command> {
    return input.fold(emptyList()) { acc, line ->
        val split = line.split(" ")
        if (line.startsWith('$'))
            acc + Command(split[1], split.getOrNull(2))
        else acc.dropLast(1) + acc.last().copy(output = acc.last().output + line)
    }
}

sealed class FileOrDir(open val fullPath: String)
data class File(
    override val fullPath: String,
    val size: Long
) : FileOrDir(fullPath)

data class Directory(
    override val fullPath: String,
) : FileOrDir(fullPath)

data class Command(val command: String, val argument: String? = null, val output: List<String> = emptyList())