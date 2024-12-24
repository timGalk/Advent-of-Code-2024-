package day08

import Reader

fun List<String>.getCharacter(x: Int, y: Int): Char = this[y][x]

data class Coordinate(val x: Int, val y: Int)

fun part01() {
    val reader = Reader()
    val grid = reader.readStandardInputToArray("src/main/resources/example.txt")
    val gridMap = mutableMapOf<Char, List<Coordinate>>()

    // Build a map of characters and their coordinates
    for (row in grid.indices) {
        for (col in grid[row].indices) {
            if (grid.getCharacter(col, row) == '.') {
                continue
            }
            if (!gridMap.containsKey(grid.getCharacter(col, row))) {
                gridMap[grid.getCharacter(col, row)] = listOf(Coordinate(col, row))
            } else {
                gridMap[grid.getCharacter(col, row)] =
                    gridMap.getValue(grid.getCharacter(col, row)) + Coordinate(col, row)
            }
        }
    }

    val antiNodes = mutableSetOf<Coordinate>()

    // Find antinodes
    for (node in gridMap) {
        val coordinates = node.value
        for (i in coordinates.indices) {
            for (j in i + 1 until coordinates.size) {
                val x1 = coordinates[i].x
                val y1 = coordinates[i].y
                val x2 = coordinates[j].x
                val y2 = coordinates[j].y


            }
        }
    }

    println("Unique antinodes: ${antiNodes.size}")
}

// Helper function to check if a coordinate is within grid bounds
fun antinodesWithinGrid(coord: Coordinate, grid: List<String>): Boolean {
    return coord.x in grid[0].indices && coord.y in grid.indices
}

fun main() {
    part01()
}
