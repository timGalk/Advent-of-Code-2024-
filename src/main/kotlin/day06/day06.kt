package day06

import java.io.BufferedReader
import java.io.File

data class Vector(val row: Int, val col: Int)
data class PathItem(val position: Vector, val direction: Vector)

data class Result(val path: List<PathItem>, val inLoop: Boolean)

fun parseGrid(lines: List<String>): Map<Vector, Char> {
    val grid = mutableMapOf<Vector, Char>()
    for ((r, row) in lines.withIndex()) {
        for ((c, char) in row.withIndex()) {
            grid[Vector(r, c)] = char
        }
    }
    return grid
}

fun walkGuard(
    grid: Map<Vector, Char>,
    guard: Vector,
    direction: Vector,
    obstacle: Vector?
): Result {
    val path = mutableListOf<PathItem>()
    val pathSet = mutableSetOf<PathItem>()
    var guardRow = guard.row
    var guardCol = guard.col
    var directionRow = direction.row
    var directionCol = direction.col

    while (true) {
        val pathItem = PathItem(Vector(guardRow, guardCol), Vector(directionRow, directionCol))
        if (pathItem in pathSet) {
            return Result(path, true) // Found a loop
        }
        path.add(pathItem)
        pathSet.add(pathItem)

        val nextGuard = Vector(guardRow + directionRow, guardCol + directionCol)

        if (nextGuard !in grid) {
            return Result(path, false) // Guard stepped outside the grid
        }

        if (nextGuard == obstacle || grid[nextGuard] == '#') {
            // Turn 90 degrees
            val temp = directionRow
            directionRow = directionCol
            directionCol = -temp
        } else {
            // Step forward
            guardRow += directionRow
            guardCol += directionCol
        }
    }
}

fun part1(lines: List<String>): Int {
    val grid = parseGrid(lines)
    val guard = findGuardPosition(grid)
    val direction = Vector(-1, 0) // Facing up
    val result = walkGuard(grid, guard, direction, null)
    if (result.inLoop) throw IllegalStateException("Unexpected loop")
    return result.path.map { it.position }.toSet().size
}

fun part2(lines: List<String>): Int {
    val grid = parseGrid(lines)
    val guardStart = findGuardPosition(grid)
    val direction = Vector(-1, 0) // Facing up

    val result = walkGuard(grid, guardStart, direction, null)
    if (result.inLoop) throw IllegalStateException("Unexpected loop")

    val obstacles = mutableSetOf<Vector>()
    var total = 0

    for (pathItem in result.path) {
        val position = pathItem.position
        val dir = pathItem.direction
        val obstacle = Vector(position.row + dir.row, position.col + dir.col)

        if (obstacle == guardStart || obstacle in obstacles || obstacle !in grid || grid[obstacle] == '#') {
            continue
        }

        obstacles.add(obstacle)

        // Skip to the first time the guard runs into this obstacle
        var newGuard: Vector? = null
        var newDirection: Vector? = null
        for (item in result.path) {
            val pos = item.position
            val dirVector = item.direction
            if (Vector(pos.row + dirVector.row, pos.col + dirVector.col) == obstacle) {
                newGuard = pos
                newDirection = dirVector
                break
            }
        }

        // If this obstacle makes the guard loop, count it
        if (newGuard != null && newDirection != null) {
            val loopResult = walkGuard(grid, newGuard, newDirection, obstacle)
            if (loopResult.inLoop) {
                total++
            }
        }
    }

    return total
}

fun findGuardPosition(grid: Map<Vector, Char>): Vector {
    for ((position, char) in grid) {
        if (char == '^') {
            return position
        }
    }
    throw IllegalStateException("Guard starting position not found")
}

fun main() {
    val filePath = "src/main/resources/day06.txt"
    val lines = File(filePath).useLines { it.toList() }

    println("Part 1: ${part1(lines)}") // Number of unique positions
    println("Part 2: ${part2(lines)}") // Number of loops
}
