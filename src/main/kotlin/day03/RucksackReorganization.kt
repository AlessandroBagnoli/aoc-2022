package day03

import utils.readFile

fun main() {

  fun List<List<String>>.valueOfCommonElems() = map { s -> s.map { it.toCharArray().toSet() } }
    .map { it.reduce { acc, elem -> acc intersect elem } }
    .map { it.first() }
    .sumOf { if (it.isLowerCase()) it.code - 'a'.code + 1 else it.code - 'A'.code + 27 }

  fun partOne(input: String) = input.split("\n").map { it.chunked(it.length / 2) }.valueOfCommonElems()

  fun partTwo(input: String) = input.split("\n").chunked(3).valueOfCommonElems()

  val input = readFile("/day03/input.txt")
  println(partOne(input))
  println(partTwo(input))

}