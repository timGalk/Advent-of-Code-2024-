package day5

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

fun main() {
    val rules = readRuleFile()
    val data = readDataSetFile()

    val map = mutableMapOf<Int, MutableSet<Int>>()
    for (rule in rules) {
        map.computeIfAbsent(rule[0]) { mutableSetOf() }.add(rule[1])
    }

    val found = mutableListOf<List<Int>>()
    val incorrect = mutableListOf<List<Int>>()
    for (row in data) {
        val flag = row.zipWithNext { a, b -> map[a]?.contains(b) == true }.all { it }

        if (flag) {
            found.add(row)
        } else {
            incorrect.add(row)
        }
    }

    val count = found.sumOf { it[it.size / 2] }

    // Task 1 result
    println(count)

    // Task 2 result
    println(part2(rules, incorrect))
}

private fun part2(rules: List<List<Int>>, data: List<List<Int>>): Int {
    var total = 0

    for (arr in data) {
        val weight = DoubleArray(arr.size)

        for (rule in rules) {
            val (val0, val1) = rule

            if (arr.contains(val0) && arr.contains(val1)) {
                val i0 = arr.indexOf(val0)
                val i1 = arr.indexOf(val1)
                weight[i0] += 100.0
                weight[i1] += 1.0
            }
        }

        val sortedArr = arr.sortedByDescending { weight[arr.indexOf(it)] }

        total += sortedArr[sortedArr.size / 2]
    }
    return total
}

private fun readRuleFile(): List<List<Int>> {
    val fileReader = FileReader("src/main/resources/day5_order_rules.txt")
    val reader: BufferedReader =BufferedReader(fileReader)
    val result = mutableListOf<List<Int>>()
    reader.useLines { lines ->
        lines.forEach { line ->
            val vars = line.split("|").map { it.toInt() }
            result.add(vars)
        }
    }
    return result
}

private fun readDataSetFile(): List<List<Int>> {
val fileReader = FileReader("src/main/resources/day5_order_rules.txt")
val reader = BufferedReader(fileReader)
    val result = mutableListOf<List<Int>>()
    reader.useLines { lines ->
        lines.forEach { line ->
            val vars = line.split(",").map { it.toInt() }
            result.add(vars)
        }
    }
    return result
}
