package day01

import utils.readFile

fun main() {

  fun parseInput(input: String) = input.split("\n\n").map { elf -> elf.split("\n").sumOf { it.toInt() } }

  fun partOne(input: String) = parseInput(input).max()

  fun partTwo(input: String) = parseInput(input).sortedDescending().take(3).sum()

  val input = readFile("/day01/input.txt")
  println(partOne(input))
  println(partTwo(input))

}