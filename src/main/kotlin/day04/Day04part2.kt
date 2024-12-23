import java.io.File

fun main() {
    val path = "PATH"
    var string = listOf<String>()
    try {
        string = File(path).readText().split("\n")
    } catch (e: Exception) {
        println("Error reading or writing file: $e")
    }

    var sortedString = convertToCharMatrix(string.filter { it.isNotEmpty() })

    println("${part2(sortedString)}")
}
fun convertToCharMatrix(data: List<String>): List<List<Char>> {
    return data.map { it.toList() }
}


fun part2(data: List<List<Char>>): Int {
    val rows = data.size
    val cols = data[0].size
    var count = 0

    val set = setOf('M', 'S')

    // Find 'A' as the center of the cross, then check the diagonals
    for (r in 1 until rows - 1) {
        for (c in 1 until cols - 1) {
            if (data[r][c] == 'A') {
                if (setOf(data[r - 1][c - 1], data[r + 1][c + 1]) == set &&
                    setOf(data[r - 1][c + 1], data[r + 1][c - 1]) == set) {
                    count++
                }
            }
        }
    }

    return count
}

