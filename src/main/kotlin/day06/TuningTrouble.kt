package day06

import utils.readFile

fun main() {

  fun String.markerPosition(markerSize: Int) =
    (0..length - markerSize).firstOrNull { i -> substring(i until i + markerSize).toSet().size == markerSize }
      ?.plus(markerSize)

  fun partOne(input: String) = input.markerPosition(4)

  fun partTwo(input: String) = input.markerPosition(14)

  val input = readFile("/day06/input.txt")
  println(partOne(input))
  println(partTwo(input))

}