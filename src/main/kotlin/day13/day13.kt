package day13

import java.io.File


fun main() {
    part01()
    part02()
}

fun part01() {
    var total = 0

    val input = File("src/main/resources/day13.txt").readText().trim() // Replace "input.txt" with the appropriate input source.
    val blocks = input.split("\n\n")

    for (block in blocks) {
        val numbers = Regex("\\d+").findAll(block).map { it.value.toInt() }.toList()

        if (numbers.size == 6) {
            val ax = numbers[0]
            val ay = numbers[1]
            val bx = numbers[2]
            val by = numbers[3]
            val px = numbers[4]
            val py = numbers[5]

            val ca = (px * by - py * bx).toDouble() / (ax * by - ay * bx)
            val cb = (px - ax * ca) / bx

            if (ca % 1 == 0.0 && cb % 1 == 0.0) {
                if (ca <= 100 && cb <= 100) {
                    total += (ca * 3 + cb).toInt()
                }
            }
        }
    }

    println(total)
}


fun part02() {
    var total = 0

    val input = File("src/main/resources/day13.txt").readText().trim() // Replace "input.txt" with the appropriate input source.
    val blocks = input.split("\n\n")

    for (block in blocks) {
        val numbers = Regex("\\d+").findAll(block).map { it.value.toLong() }.toList()

        if (numbers.size == 6) {
            var ax = numbers[0]
            var ay = numbers[1]
            var bx = numbers[2]
            var by = numbers[3]
            var px = numbers[4] + 10_000_000_000_000L
            var py = numbers[5] + 10_000_000_000_000L

            val ca = (px * by - py * bx).toDouble() / (ax * by - ay * bx)
            val cb = (px - ax * ca) / bx

            if (ca % 1 == 0.0 && cb % 1 == 0.0) {
                total += (ca * 3 + cb).toInt()
            }
        }
    }

    println(total)
}

