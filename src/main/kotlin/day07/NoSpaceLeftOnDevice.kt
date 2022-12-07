package day07

import utils.readFile

fun main() {

  data class TreeNode(val name: String, val weight: Int, val parent: TreeNode?, val children: MutableList<TreeNode>)

  fun List<String>.getFiles(indexStart: Int, parent: TreeNode) =
    subList(indexStart + 1, size)
      .takeWhile { line -> line[0] != '$' }
      .map { file ->
        val (fileWeight, fileName) = file.split(" ")
        TreeNode(fileName, if ("dir" == fileWeight) 0 else fileWeight.toInt(), parent, mutableListOf())
      }

  fun List<String>.buildTree(): TreeNode {
    val root = TreeNode("/", 0, null, mutableListOf())
    var parent = root
    withIndex().filter { it.value[0] == '$' }.forEach { (index, cmd) ->
      when {
        cmd.startsWith("$ ls") -> parent.children.addAll(getFiles(index, parent))
        cmd == "$ cd .." -> parent = parent.parent!!
        cmd == "$ cd /" -> parent = root
        else -> cmd.takeLastWhile { it != ' ' }.let { dir -> parent = parent.children.first { it.name == dir } }
      }
    }
    return root
  }

  fun TreeNode.singleNodeWeight(): Int =
    children.let { children -> if (children.isEmpty()) weight else children.sumOf { it.singleNodeWeight() } }

  fun TreeNode.nodeWeights(): List<Int> {
    val weights = mutableListOf(singleNodeWeight())
    val directories = children.filter { it.weight == 0 }
    if (directories.isNotEmpty()) {
      weights.addAll(directories.flatMap { it.nodeWeights() })
    }
    return weights
  }

  fun partOne(input: String) = input.split("\n").buildTree().nodeWeights().filter { it < 100000 }.sum()

  fun partTwo(input: String) = input.split("\n").let { s ->
    s.buildTree().nodeWeights().sorted()
      .let { weights -> weights.first { it >= 30000000 - (70000000 - weights.last()) } }
  }

  val input = readFile("/day07/input.txt")
  println(partOne(input))
  println(partTwo(input))

}