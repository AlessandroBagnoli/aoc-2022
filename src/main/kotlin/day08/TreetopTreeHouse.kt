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

  fun viewDistanceRight(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int): Int {
    val trees = matrix[rowIndex].drop(columnIndex + 1)
    val windows = trees.windowed(2, 1)
    val count = windows.takeWhile { it[0] < it[1] }.count()
    return if (windows.isNotEmpty()) count + 1 else count
  }

  fun viewDistanceLeft(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int): Int {
    val trees = matrix[rowIndex].take(columnIndex).reversed()
    val windows = trees.windowed(2, 1)
    val count = windows.takeWhile { it[0] < it[1] }.count()
    return if (windows.isNotEmpty()) count + 1 else count
  }

  fun viewDistanceUp(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int): Int {
    val trees = matrix.take(rowIndex).map { it[columnIndex] }.reversed()
    val windows = trees.windowed(2, 1)
    val count = windows.takeWhile { it[0] < it[1] }.count()
    return if (windows.isNotEmpty()) count + 1 else count
  }

  fun viewDistanceDown(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int): Int {
    val trees = matrix.drop(rowIndex + 1).map { it[columnIndex] }
    val windows = trees.windowed(2, 1)
    val count = windows.takeWhile { it[0] < it[1] }.count()
    return if (windows.isNotEmpty()) count + 1 else count
  }

  fun List<List<Int>>.toVisibilityMap() = mapIndexed { rowIndex, row ->
    row.mapIndexed { columnIndex, elem ->
      isVisibleFromRight(this, rowIndex, columnIndex, elem)
          || isVisibleFromLeft(this, rowIndex, columnIndex, elem)
          || isVisibleFromUp(this, rowIndex, columnIndex, elem)
          || isVisibleFromDown(this, rowIndex, columnIndex, elem)
    }
  }

  fun List<List<Int>>.toScenicScoreMap() = mapIndexed { rowIndex, row ->
    List(row.size) { columnIndex ->
      viewDistanceRight(this, rowIndex, columnIndex) *
          viewDistanceLeft(this, rowIndex, columnIndex) *
          viewDistanceUp(this, rowIndex, columnIndex) *
          viewDistanceDown(this, rowIndex, columnIndex)
    }
  }

  fun partOne(input: String) = input.createMatrix().toVisibilityMap().flatten().count { it }

  fun partTwo(input: String) = input.createMatrix().toScenicScoreMap()//.flatten().maxOf { it }

  val input = readFile("/day08/input.txt")
  println(partOne(input))
  println(partTwo(input))

}