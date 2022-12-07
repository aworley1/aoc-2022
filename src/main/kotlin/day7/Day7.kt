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

fun createFileMap(commands: List<Command>): FileMap {
    var currentDirectory = ""
    val output = mutableMapOf<String, FileOrDir>("/" to Directory(""))

    commands.forEach {
        when (it.command) {
            "cd" -> currentDirectory += it.argument!!
            "ls" -> it.output.forEach {
                val fileOrDir = it.toFileOrDir()
                output.put("$currentDirectory${fileOrDir.name}", fileOrDir)
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