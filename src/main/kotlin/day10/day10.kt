import java.io.File
import java.util.ArrayDeque

data class Position(val x: Int, val y: Int)

fun main() {
    val path = "src/main/resources/day10.txt" // Replace with your actual input path
    val map = parseInput(path)

    val rows = map.size
    val cols = if (rows > 0) map[0].size else 0
    val directions = listOf(Position(-1, 0), Position(1, 0), Position(0, -1), Position(0, 1))

    var totalScore = 0

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (map[i][j] == 0) { // Identify trailheads
                totalScore += calculateTrailheadScore(i, j, map, rows, cols, directions)
            }
        }
    }

    println("Total score of all trailheads: $totalScore")
}

// Function to parse the input grid from the file
fun parseInput(path: String): List<List<Int>> {
    return File(path).readLines().map { line ->
        line.trim().map { it.digitToInt() }
    }
}

// Function to calculate the score of a single trailhead
fun calculateTrailheadScore(
    startX: Int, startY: Int, map: List<List<Int>>,
    rows: Int, cols: Int, directions: List<Position>
): Int {
    val visited = Array(rows) { BooleanArray(cols) }
    val stack = ArrayDeque<Position>()
    val reachableNines = mutableSetOf<Position>()

    stack.push(Position(startX, startY))

    while (stack.isNotEmpty()) {
        val current = stack.pop()
        val cx = current.x
        val cy = current.y

        if (visited[cx][cy]) continue
        visited[cx][cy] = true

        if (map[cx][cy] == 9) {
            reachableNines.add(Position(cx, cy))
        }

        for (dir in directions) {
            val nx = cx + dir.x
            val ny = cy + dir.y

            if (nx in 0 until rows && ny in 0 until cols && !visited[nx][ny]) {
                if (map[nx][ny] == map[cx][cy] + 1) {
                    stack.push(Position(nx, ny))
                }
            }
        }
    }

    return reachableNines.size
}
