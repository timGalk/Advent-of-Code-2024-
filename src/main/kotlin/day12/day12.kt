package day12

import Reader

data class Position(val x: Int, val y: Int)

val directions = listOf(Position(-1, 0), Position(1, 0), Position(0, -1), Position(0, 1))

val reader = Reader()
val path = "src/main/resources/day12.txt"
val grid = reader.gridCreator(path)
val rows = grid.size
val cols = grid[0].size

fun main (){
    part01()
}
fun part01() {
    val regions = mutableListOf<Set<Position>>()
    val seen = mutableSetOf<Position>()
    for (x in 0 until rows) {
        for (y in 0 until cols) {
            if (Position(x, y) in seen) continue
            val region = mutableSetOf(Position(x, y))
            val crop = grid[x][y]
            val q = ArrayDeque<Position>()
            q.add(Position(x, y))
            seen.add(Position(x, y))

            while (q.isNotEmpty()) {
                val current = q.removeFirst()

                for (dir in directions) {
                    val nx = current.x + dir.x
                    val ny = current.y + dir.y
                    if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) continue
                    if (grid[nx][ny] != crop) continue
                    if (Position(nx, ny) in region) continue
                    region.add(Position(nx, ny))
                    q.add(Position(nx, ny))
                    seen.add(Position(nx, ny))
                }
            }
            regions.add(region)
        }
    }

    val result = regions.sumOf { it.size * perimeter(it) }
    println(result)
}

fun perimeter(region: Set<Position>): Int {
    var output = 0
    for (pos in region) {
        output += 4
        for (dir in directions) {
            val nx = pos.x + dir.x
            val ny = pos.y + dir.y
            if (Position(nx, ny) in region) {
                output--
            }
        }
    }
    return output
}

fun part02(){

}

