package day11

import utils.readFile

fun main() {

  data class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisibleBy: Int,
    val test: (Long) -> Boolean,
    val monkeyIfTrue: Int,
    val monkeyIfFalse: Int,
    var inspectionsDone: Long = 0
  )

  fun String.operation(): (Long, Long) -> Long =
    when (this) {
      "+" -> { a, b -> a + b }
      "-" -> { a, b -> a - b }
      "/" -> { a, b -> a / b }
      "*" -> { a, b -> a * b }
      else -> throw IllegalArgumentException("That's not a supported operator")
    }

  fun String.numberOrCurrent(current: Long) = takeIf { it != "old" }?.toLong() ?: current

  fun String.toMonkey(): Monkey {
    val rows = split("\n")
    val id = rows[0].removeSurrounding("Monkey ", ":").toInt()
    val items = rows[1].substringAfter(": ").split(",").map { it.trim().toLong() }.toMutableList()
    val operators = rows[2].substringAfter("= ").split(" ")
    val operation = { current: Long ->
      operators[1].operation()(operators[0].numberOrCurrent(current), operators[2].numberOrCurrent(current))
    }
    val divisibleBy = rows[3].substringAfter("by ").toInt()
    val test = { current: Long -> current % divisibleBy == 0L }
    val monkeyToThrowAtIfTestTrue = rows[4].substringAfter("to monkey ").toInt()
    val monkeyToThrowAtIfTestFalse = rows[5].substringAfter("to monkey ").toInt()
    return Monkey(
      id, items, operation, divisibleBy, test, monkeyToThrowAtIfTestTrue, monkeyToThrowAtIfTestFalse
    )
  }

  fun List<Monkey>.start(rounds: Int, op: (Monkey, Long) -> Long): Long {
    (1..rounds).onEach {
      forEach { monkey ->
        monkey.items.forEach {
          op(monkey, it).let { itemProcessed ->
            get(monkey.monkeyIfTrue).items.takeIf { monkey.test(itemProcessed) }
              ?.add(itemProcessed) ?: get(monkey.monkeyIfFalse).items.add(itemProcessed)
          }.also { monkey.inspectionsDone++ }
        }.also { monkey.items.clear() }  // we can assume the monkey has thrown everything
      }
    }
    return map { it.inspectionsDone }.sortedDescending().take(2).fold(1L) { acc, elem -> acc * elem }
  }

  fun partOne(input: String) =
    input.split("\n\n").map { it.toMonkey() }.start(20) { monkey, item -> monkey.operation(item) / 3 }

  fun partTwo(input: String) = input.split("\n\n").map { it.toMonkey() }.let { monkeys ->
    monkeys.map { it.divisibleBy }.reduce { acc, i -> acc * i }
      .let { monkeys.start(10000) { monkey, item -> monkey.operation(item) % it } }
  }


  val input = readFile("/day11/input.txt")
  println(partOne(input))
  println(partTwo(input))

}