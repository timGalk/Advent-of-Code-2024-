package day08

import Reader

fun List<String>.getCharacter(x: Int, y: Int): Char = this[y][x]

fun mapCreator(grid: List<String>): MutableMap<Char, MutableList<Coordinate>> {
    val gridMap = mutableMapOf<Char, MutableList<Coordinate>>()

    for (row in grid.indices) {
        for (col in grid[row].indices) {
            val char = grid.getCharacter(col, row)
            if (char == '.') continue
            gridMap.computeIfAbsent(char) { mutableListOf() }.add(Coordinate(col, row))
        }
    }

    return gridMap
}

data class Coordinate(val x: Int, val y: Int)

fun antinodesWithinGrid(coord: Coordinate, grid: List<String>): Boolean {
    return coord.x >= 0 && coord.x < grid[0].length && coord.y >= 0 && coord.y < grid.size}

fun part01() {
    val reader = Reader()
    val grid = reader.readStandardInputToArray("src/main/resources/example.txt")
    val gridMap = mapCreator(grid)

    val antiNodes = mutableSetOf<Coordinate>()

    // Find antinodes
    for ((_, coordinates) in gridMap) {
        for (i in coordinates.indices) {
            for (j in i + 1 until coordinates.size) {
                val x1 = coordinates[i].x
                val y1 = coordinates[i].y
                val x2 = coordinates[j].x
                val y2 = coordinates[j].y

                val dx = x2 - x1
                val dy = y2 - y1

                val fx = x1 - dx
                val fy = y1 - dy
                val gx = x2 + dx
                val gy = y2 + dy

                if (antinodesWithinGrid(Coordinate(fx, fy), grid)) {
                    antiNodes.add(Coordinate(fx, fy))
                }
                if (antinodesWithinGrid(Coordinate(gx, gy), grid)) {
                    antiNodes.add(Coordinate(gx, gy))
                }
            }
        }
    }

    println("Unique antinodes: ${antiNodes.size}")
}


fun part02() {
    val reader = Reader()
    val grid = reader.readStandardInputToArray("src/main/resources/day08a.txt")
    val gridMap = mapCreator(grid)


    val antiNodes = mutableSetOf<Coordinate>()

    // Find antinodes
    for ((_, coordinates) in gridMap) {
        // If there is more than one antenna, check for antinodes in line
        if (coordinates.size > 1) {
            for (i in coordinates.indices) {
                for (j in i + 1 until coordinates.size) {
                    val x1 = coordinates[i].x
                    val y1 = coordinates[i].y
                    val x2 = coordinates[j].x
                    val y2 = coordinates[j].y

                    // Get the direction vectors
                    val dx = x2 - x1
                    val dy = y2 - y1

                    var fx = x1 - dx
                    var fy = y1 - dy
                    var gx = x2 + dx
                    var gy = y2 + dy

                    // Add the positions of antennas themselves as potential antinodes
                    antiNodes.add(Coordinate(x1, y1))
                    antiNodes.add(Coordinate(x2, y2))

                    while (antinodesWithinGrid(Coordinate(fx, fy), grid)) {
                        antiNodes.add(Coordinate(fx, fy))
                        fx -= dx
                        fy -= dy
                    }
                    while (antinodesWithinGrid(Coordinate(gx, gy), grid)) {
                        antiNodes.add(Coordinate(gx, gy))
                        gx += dx
                        gy += dy
                    }
                }
            }
        }
    }
    println("Unique antinodes: ${antiNodes.size}")
}






fun main() {
    part01()
    part02()
}
