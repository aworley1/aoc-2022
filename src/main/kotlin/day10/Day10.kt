package day10

import day10.State.*
import util.getInputLines

fun main() {
    println(part1(getInputLines(10)))
}

fun part1(input: List<String>): Int {
    val computers = mutableListOf(Computer())

    val instructions = input.map {
        it.split(" ").let { Pair(it[0], it.getOrNull(1)?.toInt()) }
    }

    instructions.forEach {
        when (it.first) {
            "noop" -> computers.addAll(computers.last().noop())
            "addx" -> computers.addAll(computers.last().addx(it.second!!))
            else -> TODO()
        }
    }


    return computers.findComputerExecuting(20).findSignalStrength() +
            computers.findComputerExecuting(60).findSignalStrength() +
            computers.findComputerExecuting(100).findSignalStrength() +
            computers.findComputerExecuting(140).findSignalStrength() +
            computers.findComputerExecuting(180).findSignalStrength() +
            computers.findComputerExecuting(220).findSignalStrength()
}

private fun List<Computer>.findComputerExecuting(cycle: Int) =
    filter { it.state == EXECUTING }.single { it.clockCycle == cycle }

private fun Computer.findSignalStrength() = x * clockCycle

data class Computer(
    val clockCycle: Int = 0,
    val x: Int = 1,
    val state: State = AFTER_EXECUTING
) {
    fun addx(num: Int): List<Computer> {
        return listOf(
            copy(clockCycle = clockCycle + 1, state = BEFORE_EXECUTING),
            copy(clockCycle = clockCycle + 1, state = EXECUTING),
            copy(clockCycle = clockCycle + 1, state = AFTER_EXECUTING),
            copy(clockCycle = clockCycle + 2, state = BEFORE_EXECUTING),
            copy(clockCycle = clockCycle + 2, state = EXECUTING),
            copy(clockCycle = clockCycle + 2, x = x + num, state = AFTER_EXECUTING),
        )
    }

    fun noop(): List<Computer> = listOf(
        copy(clockCycle = clockCycle + 1, state = BEFORE_EXECUTING),
        copy(clockCycle = clockCycle + 1, state = EXECUTING),
        copy(clockCycle = clockCycle + 1, state = AFTER_EXECUTING)
    )
}

enum class State {
    AFTER_EXECUTING,
    BEFORE_EXECUTING,
    EXECUTING
}