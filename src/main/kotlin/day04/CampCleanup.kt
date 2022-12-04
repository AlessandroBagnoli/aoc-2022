package day04

import utils.readFile

fun main() {

  fun String.tokenize() = split("-").let { (it[0].toInt()..it[1].toInt()) }

  fun String.toListOfPairs() =
    split("\n").map { row -> row.split(",").let { comp -> Pair(comp[0].tokenize(), comp[1].tokenize()) } }

  fun Pair<IntRange, IntRange>.oneFullyContainOther() = first.all { it in second } or second.all { it in first }

  fun Pair<IntRange, IntRange>.overlapAtAll() = first.any { it in second }

  fun partOne(input: String) = input.toListOfPairs().count { it.oneFullyContainOther() }

  fun partTwo(input: String) = input.toListOfPairs().count { it.overlapAtAll() }

  val input = readFile("/day04/input.txt")
  println(partOne(input))
  println(partTwo(input))

}