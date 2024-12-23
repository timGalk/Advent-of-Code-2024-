import java.io.File

fun main () {
    val path = "PATH"
    var string = listOf<String>()
    try {
        string = File(path).readText().split("\n")
    } catch (e: Exception) {
        println("Error reading or writing file: $e")
    }

    var sortedString = string.filter { it.isNotEmpty() }

    println("${countWordOccurrences(sortedString, "XMAS")}")



}
fun countWordOccurrences(grid: List<String>, word: String): Int {
    // Check if grid is empty or has rows of unequal lengths
    if (grid.isEmpty() || grid.any { it.length != grid[0].length }) {
        throw IllegalArgumentException("Grid must not be empty, and all rows must have the same length.")
    }

    val rows = grid.size
    val cols = grid[0].length
    val directions = listOf(
        Pair(0, 1),   // Right
        Pair(1, 0),   // Down
        Pair(1, 1),   // Down-Right (Main Diagonal)
        Pair(1, -1),  // Down-Left (Anti-Diagonal)
        Pair(0, -1),  // Left (Backwards Horizontal)
        Pair(-1, 0),  // Up (Backwards Vertical)
        Pair(-1, -1), // Up-Left (Backwards Main Diagonal)
        Pair(-1, 1)   // Up-Right (Backwards Anti-Diagonal)
    )

    fun isValid(x: Int, y: Int) = x in 0 until rows && y in 0 until cols

    fun findWord(x: Int, y: Int, dx: Int, dy: Int): Boolean {
        for (i in word.indices) {
            val nx = x + i * dx
            val ny = y + i * dy
            if (!isValid(nx, ny) || grid[nx][ny] != word[i]) return false
        }
        return true
    }

    var count = 0

    for (x in 0 until rows) {
        for (y in 0 until cols) {
            for ((dx, dy) in directions) {
                if (findWord(x, y, dx, dy)) count++
            }
        }
    }

    return count
}