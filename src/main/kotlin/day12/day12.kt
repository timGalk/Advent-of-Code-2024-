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
    part02()
}
fun part01() {
    val regions = regions()


    val result = regions.sumOf { it.size * perimeter(it) }
    println(result)
}
fun regions(): List<Set<Position>> {
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

    return regions
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
    val regions = regions()

    val result = regions.sumOf { it.size * sides(it) }
    println(result)

}

fun sides(region: Set<Position>): Int {
    val cornerCandidates = mutableSetOf<Pair<Double, Double>>()

    // Step 1: Generate corner candidates
    for (pos in region) {
        val (r, c) = pos
        listOf(
            Pair(r - 0.5, c - 0.5),
            Pair(r + 0.5, c - 0.5),
            Pair(r + 0.5, c + 0.5),
            Pair(r - 0.5, c + 0.5)
        ).forEach { cornerCandidates.add(it) }
    }

    var corners = 0

    // Step 2: Analyze each corner candidate
    for ((cr, cc) in cornerCandidates) {
        val config = listOf(
            region.contains(Position((cr - 0.5).toInt(), (cc - 0.5).toInt())),
            region.contains(Position((cr + 0.5).toInt(), (cc - 0.5).toInt())),
            region.contains(Position((cr + 0.5).toInt(), (cc + 0.5).toInt())),
            region.contains(Position((cr - 0.5).toInt(), (cc + 0.5).toInt()))
        )
        val number = config.count { it }

        // Step 3: Calculate corners based on configurations
        corners += when (number) {
            1 -> 1
            2 -> if (config == listOf(true, false, true, false) || config == listOf(false, true, false, true)) 2 else 0
            3 -> 1
            else -> 0
        }
    }

    return corners
}


