package day09

import utils.readFile
import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {

  data class Point(val x: Int = 0, val y: Int = 0) {
    fun move(direction: Char): Point =
      when (direction) {
        'U' -> copy(y = y - 1)
        'D' -> copy(y = y + 1)
        'L' -> copy(x = x - 1)
        'R' -> copy(x = x + 1)
        else -> throw IllegalArgumentException("Unknown Direction: $direction")
      }

    fun moveTowards(other: Point): Point =
      Point(
        (other.x - x).sign + x,
        (other.y - y).sign + y
      )

    fun touches(other: Point): Boolean =
      (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1

  }

  fun String.followPath(knots: Int): Int {
    val rope = Array(knots) { Point() }
    val tailVisits = mutableSetOf(Point())

    forEach { direction ->
      rope[0] = rope[0].move(direction)
      rope.indices.windowed(2, 1) { (head, tail) ->
        if (!rope[head].touches(rope[tail])) {
          rope[tail] = rope[tail].moveTowards(rope[head])
        }
      }
      tailVisits += rope.last()
    }
    return tailVisits.size
  }

  fun List<String>.parseInput(): String =
    joinToString("") { row ->
      val direction = row.substringBefore(" ")
      val numberOfMoves = row.substringAfter(' ').toInt()
      direction.repeat(numberOfMoves)
    }


  fun partOne(input: String): Int = input.split("\n").parseInput().followPath(2)

  fun partTwo(input: String): Int = input.split("\n").parseInput().followPath(10)

  val input = readFile("/day09/input.txt")
  println(partOne(input))
  println(partTwo(input))

}