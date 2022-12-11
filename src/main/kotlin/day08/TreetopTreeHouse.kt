package day08

import utils.readFile

fun main() {

  fun String.createMatrix() = split("\n").map { s -> s.map { it.digitToInt() } }

  fun isVisibleFromRight(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int, elem: Int) =
    matrix[rowIndex].drop(columnIndex + 1).all { it < elem }

  fun isVisibleFromLeft(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int, elem: Int) =
    matrix[rowIndex].take(columnIndex).all { it < elem }

  fun isVisibleFromUp(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int, elem: Int) =
    matrix.take(rowIndex).map { it[columnIndex] }.all { it < elem }

  fun isVisibleFromDown(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int, elem: Int) =
    matrix.drop(rowIndex + 1).map { it[columnIndex] }.all { it < elem }

  fun List<List<Int>>.toVisibilityMap() = mapIndexed { rowIndex, row ->
    row.mapIndexed { columnIndex, elem ->
      isVisibleFromRight(this, rowIndex, columnIndex, elem)
          || isVisibleFromLeft(this, rowIndex, columnIndex, elem)
          || isVisibleFromUp(this, rowIndex, columnIndex, elem)
          || isVisibleFromDown(this, rowIndex, columnIndex, elem)
    }
  }

  fun partOne(input: String) = input.createMatrix().toVisibilityMap().flatten().count { it }

  val input = readFile("/day08/input.txt")
  println(partOne(input))
  //println(partTwo(input))

}