import java.io.File

class Reader {
    fun readFileToNestedIntArray(filePath: String): List<List<Int>> {
        return File(filePath).readLines()
            .map { line ->
                line.split(" ")
                    .mapNotNull { it.trim().toIntOrNull() }
            }
    }

    fun readStandardInputToArray(filePath: String): List<String> {
        return File(filePath).readLines()
    }

    fun readString(filePath: String): String {
        return File(filePath).readText()
    }
    fun readStringAsIntArr(filePath: String): List<Int> {
        return File(filePath).readText().split(" ").map { it.toInt() }
    }
    fun readFileToNestedCharArray(filePath: String): List<List<Char>> {
        return File(filePath).readLines()
            .map { line ->
                line.split(" ")
                    .mapNotNull { it.trim().first() }
            }
    }
}