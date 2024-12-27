package day11
import Reader
import day09.getPart2Result

val reader = Reader()
val path = "src/main/resources/day11.txt"
val stones = reader.readStringAsIntArr(path)
    .map { it.toLong() }





fun part01() {
    /**
    Steps to Simulate a Single Blink:
    Apply Rule 1:

    If a stone's number is 0, it is replaced with a stone engraved with 1.

    Apply Rule 2:

    If the stone's number has an even number of digits, it is split into two stones:
    The left half of the digits becomes the number engraved on the left stone.
    The right half of the digits becomes the number engraved on the right stone.
    Remove leading zeroes in the resulting numbers, if any.

    Apply Rule 3:

    For all other stones (those not handled by Rule 1 or Rule 2), the stone is replaced by
    a new stone engraved with the old stone's number multiplied by 2024.
    Preserve Order:

    The new stones replace the old ones in the same order.


     */

    var currentStones = stones
    for (i in 0 until 25) {
        currentStones = rules(currentStones)
    }
    println(currentStones.size)
}
fun rules(stones: List<Long>): List<Long> {
    val newStones = mutableListOf<Long>()
    for (stone in stones) {
        if (stone == 0L) {
            newStones.add(1)
        } else if (stone.toString().length % 2 == 0) {
            val half = stone.toString().length / 2
            val left = stone.toString().substring(0, half).toLong()
            val right = stone.toString().substring(half).toLong()
            newStones.add(left)
            newStones.add(right)
        } else {
            newStones.add(stone * 2024)
        }
    }
    return newStones
}


fun part02() {
    /**
     * Contains optimizeed algorithm to reduce the number of iterations
     */
    var stoneStorage = listToMap(stones)

    repeat(75) {
        stoneStorage = optimizedAlgorithm(stoneStorage)
    }
    println(stoneStorage.values.sum())

}
fun optimizedAlgorithm(stoneStorage: MutableMap<Long, Long>): MutableMap<Long, Long> {
    val result = linkedMapOf<Long, Long>() // Preserve order with LinkedHashMap

    for ((stone, value) in stoneStorage) {
        if (stone == 0L) {
            // Rule 1: Stone 0 becomes 1, accumulate its value
            result[1] = result.getOrDefault(1, 0L) + value
        } else {
            val stoneStr = stone.toString()
            if (stoneStr.length % 2 == 0) {
                // Rule 2: Split stones with even number of digits
                val half = stoneStr.length / 2
                val left = stoneStr.substring(0, half).toLongOrNull() ?: 0L
                val right = stoneStr.substring(half).toLongOrNull() ?: 0L
                result[left] = result.getOrDefault(left, 0L) + value
                result[right] = result.getOrDefault(right, 0L) + value
            } else {
                // Rule 3: Stones with odd digits are multiplied by 2024
                val transformedStone = stone * 2024
                result[transformedStone] = result.getOrDefault(transformedStone, 0L) + value
            }
        }
    }

    return result
}



fun listToMap(stones: List<Long>): MutableMap<Long, Long> {
    val map = mutableMapOf<Long, Long>()
    for (stone in stones) {
        map[stone] = map.getOrDefault(stone, 0) + 1
    }
    return map
}



fun main() {
    part01()
    part02()
}