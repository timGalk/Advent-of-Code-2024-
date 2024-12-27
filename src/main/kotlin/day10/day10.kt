package day10
import java.io.File
import java.util.ArrayDeque

data class Position(val x: Int, val y: Int)

val path = "src/main/resources/day10.txt" // Replace with your actual input path
val map = parseInput(path)
val rows = map.size
val cols = if (rows > 0) map[0].size else 0
val directions = listOf(Position(-1, 0), Position(1, 0), Position(0, -1), Position(0, 1))

fun part01() {


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

fun part02 () {


    var totalRating = 0

    // Memoization table to store results for (x, y) positions
    val memo = mutableMapOf<Pair<Int, Int>, Int>()

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            if (map[i][j] == 0) { // Identify trailheads
                totalRating += calculateTrailheadRating(i, j, map, rows, cols, directions, memo)
            }
        }
    }

    println("Total rating of all trailheads: $totalRating")
}

// Function to calculate the rating of a single trailhead
fun calculateTrailheadRating(
    startX: Int, startY: Int, map: List<List<Int>>,
    rows: Int, cols: Int, directions: List<Position>,
    memo: MutableMap<Pair<Int, Int>, Int>
): Int {
    return dfsCountDistinctPaths(startX, startY, map, rows, cols, directions, memo)
}

// Recursive function to count distinct paths to height 9
fun dfsCountDistinctPaths(
    x: Int, y: Int, map: List<List<Int>>,
    rows: Int, cols: Int, directions: List<Position>,
    memo: MutableMap<Pair<Int, Int>, Int>
): Int {
    // Check memoized result
    val key = x to y
    if (key in memo) return memo[key]!!

    // Base case: If we reach height 9, this is a valid trail
    if (map[x][y] == 9) return 1

    var count = 0

    for (dir in directions) {
        val nx = x + dir.x
        val ny = y + dir.y

        if (nx in 0 until rows && ny in 0 until cols) {
            if (map[nx][ny] == map[x][y] + 1) {
                count += dfsCountDistinctPaths(nx, ny, map, rows, cols, directions, memo)
            }
        }
    }

    // Memoize the result for this position
    memo[key] = count
    return count
}

fun main() {
    part01()
    part02()
}