package day10

import utils.readFile

fun main() {

  fun String.toCycles() = mutableListOf<Int>().let { list ->
    split("\n").forEach { s -> list.addAll(listOf(0).takeIf { s == "noop" } ?: listOf(0, s.split(" ")[1].toInt())) }
      .let { list }
  }

  fun partOne(input: String) = input.toCycles()
    .let { cycles -> (20..220 step 40).map { cycles.take(it - 1).fold(1) { acc, x -> acc + x } * it }.sumOf { it } }

  fun partTwo(input: String): String {
    var x = 1
    val crt = Array(6) { CharArray(40) }
    input.toCycles().chunked(40).forEachIndexed { rowIndex, row ->
      row.forEachIndexed { columnIndex, elem ->
        crt[rowIndex][columnIndex] = '#'.takeIf { (x - 1..x + 1).contains(columnIndex) } ?: '.'
        x += elem
      }
    }
    return crt.map { String(it) }.reduce { acc, s -> acc.plus("\n").plus(s) }
  }

  val input = readFile("/day10/input.txt")
  println(partOne(input))
  println(partTwo(input))

}