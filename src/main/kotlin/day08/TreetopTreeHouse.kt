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

  // TODO consider lists of one tree and zero tree (?)
  fun viewDistanceRight(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int, elem: Int) =
    matrix[rowIndex].drop(columnIndex + 1).takeWhile { it < elem }.count()

  fun viewDistanceLeft(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int, elem: Int) =
    matrix[rowIndex].take(columnIndex).reversed().takeWhile { it < elem }.count()
  
  fun viewDistanceUp(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int, elem: Int) =
    matrix.take(rowIndex).map { it[columnIndex] }.reversed().takeWhile { it < elem }.count()
  
  fun viewDistanceDown(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int, elem: Int) =
    matrix.drop(rowIndex + 1).map { it[columnIndex] }.takeWhile { it < elem }.count()


  fun List<List<Int>>.toVisibilityMap() = mapIndexed { rowIndex, row ->
    row.mapIndexed { columnIndex, elem ->
      isVisibleFromRight(this, rowIndex, columnIndex, elem)
          || isVisibleFromLeft(this, rowIndex, columnIndex, elem)
          || isVisibleFromUp(this, rowIndex, columnIndex, elem)
          || isVisibleFromDown(this, rowIndex, columnIndex, elem)
    }
  }

  fun List<List<Int>>.toScenicScoreMap() = mapIndexed { rowIndex, row ->
    row.mapIndexed { columnIndex, elem ->
      viewDistanceRight(this, rowIndex, columnIndex, elem) *
          viewDistanceLeft(this, rowIndex, columnIndex, elem) *
          viewDistanceUp(this, rowIndex, columnIndex, elem) *
          viewDistanceDown(this, rowIndex, columnIndex, elem)
    }
  }


  fun partOne(input: String) = input.createMatrix().toVisibilityMap().flatten().count { it }

  fun partTwo(input: String) = input.createMatrix().toScenicScoreMap()//.flatten().maxOf { it }

  val input = readFile("/day08/input.txt")
  println(partOne(input))
  println(partTwo(input))

}