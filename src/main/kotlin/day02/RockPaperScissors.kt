package day02

import utils.readFile

fun main() {

  val winPt = 6
  val drawPt = 3
  val losePt = 0

  val rockPt = 1
  val paperPt = 2
  val scissorsPt = 3
  
  val opponentRock = "A"
  val opponentPaper = "B"
  val opponentScissors = "C"
  val myRock = "X"
  val myPaper = "Y"
  val myScissors = "Z"
  
  val mapPartOne = mapOf(
    "$opponentRock $myRock" to drawPt + rockPt,
    "$opponentRock $myPaper" to winPt + paperPt,
    "$opponentRock $myScissors" to losePt + scissorsPt,
    "$opponentPaper $myRock" to losePt + rockPt,
    "$opponentPaper $myPaper" to drawPt + paperPt,
    "$opponentPaper $myScissors" to winPt + scissorsPt,
    "$opponentScissors $myRock" to winPt + rockPt,
    "$opponentScissors $myPaper" to losePt + paperPt,
    "$opponentScissors $myScissors" to drawPt + scissorsPt,
  )

  val needToLose = "X"
  val needToDraw = "Y"
  val needToWin = "Z"
  
  val mapPartTwo = mapOf(
    "$opponentRock $needToLose" to losePt + scissorsPt,
    "$opponentRock $needToDraw" to drawPt + rockPt,
    "$opponentRock $needToWin" to winPt + paperPt,
    "$opponentPaper $needToLose" to losePt + rockPt,
    "$opponentPaper $needToDraw" to drawPt + paperPt,
    "$opponentPaper $needToWin" to winPt + scissorsPt,
    "$opponentScissors $needToLose" to losePt + paperPt,
    "$opponentScissors $needToDraw" to drawPt + scissorsPt,
    "$opponentScissors $needToWin" to winPt + rockPt,
  )

  fun partOne(input: String) = input.split("\n").map { mapPartOne[it] }.sumOf { it ?: 0 }

  fun partTwo(input: String) = input.split("\n").map { mapPartTwo[it] }.sumOf { it ?: 0 }

  val input = readFile("/day02/input.txt")
  println(partOne(input))
  println(partTwo(input))

}