package day05

import utils.readFile

fun main() {

  fun String.countStacks() = (this.length + 4 - 1) / 4

  fun String.toMoves() =
    split("\n").map {
      Triple(
        it.substringAfter("move").substringBefore("from").trim().toInt(),
        it.substringAfter("from").substringBefore("to").trim().toInt(),
        it.substringAfter("to").trim().toInt()
      )
    }

  fun String.toStacks() = split("\n").reversed().let { it.takeLast(it.size - 1) }.let { rows ->
    rows[0].countStacks().let { totStacks ->
      (1..totStacks).map { ArrayDeque<Char>() }.onEachIndexed { index, stack ->
        rows.forEach { row ->
          row.chunked(4)[index].takeIf { chunk -> chunk.any { it.isLetter() } }
            ?.let { chunk -> stack.add(chunk.first { it.isLetter() }) }
        }
      }
    }
  }

  fun List<ArrayDeque<Char>>.reduce() = map { String(it.takeLast(1).toCharArray()) }.reduce { acc, s -> acc.plus(s) }

  fun List<ArrayDeque<Char>>.applyMoves(moves: List<Triple<Int, Int, Int>>) =
    moves.forEach { move -> (1..move.first).map { get(move.third - 1).add(get(move.second - 1).removeLast()) } }
      .let { this }

  fun List<ArrayDeque<Char>>.applyMovesSecond(moves: List<Triple<Int, Int, Int>>) =
    moves.forEach { move ->
      (1..move.first).map { get(move.second - 1).removeLast() }.reversed().let { get(move.third - 1).addAll(it) }
    }
      .let { this }

  fun String.parse() = split("\n\n").let { s -> Pair(s[0].toStacks(), s[1].toMoves()) }

  fun partOne(input: String) = input.parse().let { it.first.applyMoves(it.second) }.reduce()

  fun partTwo(input: String) = input.parse().let { it.first.applyMovesSecond(it.second) }.reduce()

  val input = readFile("/day05/input.txt")
  println(partOne(input))
  println(partTwo(input))

}