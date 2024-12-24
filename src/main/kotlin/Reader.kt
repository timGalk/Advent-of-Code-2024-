import java.io.File

class Reader {
    fun readFileToNestedArray(filePath: String): List<List<Int>> {
        return File(filePath).readLines()
            .map { line ->
                line.split(" ")
                    .mapNotNull { it.trim().toIntOrNull() }
            }
    }

    fun readStandardInputToArray (filePath: String): List<String> {
        return File(filePath).readLines()
    }
}