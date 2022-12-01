package util

import java.io.File

fun getSampleInputLines(day: Int, sample: Int) = File("./sample-inputs/day$day-sample$sample.txt").readLines()
fun getSampleInputText(day: Int, sample: Int) = File("./sample-inputs/day$day-sample$sample.txt").readText()

fun getInputLines(day: Int) = File("./inputs/day$day.txt").readLines()
fun getInputText(day: Int) = File("./inputs/day$day.txt").readText()