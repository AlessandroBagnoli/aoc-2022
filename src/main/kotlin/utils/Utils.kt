package utils

fun readFile(filePath: String) = object {}.javaClass.getResource(filePath)!!.readText()