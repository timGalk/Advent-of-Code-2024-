
import java.io.File

fun main() {

    part02()
}
// Part01

fun part01(){
    val dict = parser("day07input.txt")

    var result = 0L
    for (line in dict ){
        if (canFormKey(line.key, line.value)){
            result += line.key
        }
    }


    println(result)

}
// Recursive function 
fun canFormKey(key: Long, values: List<Int>): Boolean {
    fun check(current: Long, index: Int): Boolean {
        if (index == values.size) {
            return current == key
        }

        val nextValue = values[index].toLong()
        return check(current + nextValue, index + 1) ||
                check(current * nextValue, index + 1)
    }

    return if (values.isNotEmpty()) check(values[0].toLong(), 1) else false
}




fun part02(){

    val dict = parser("src/main/resources/day07input.txt")
    var result = 0L
    for (line in dict ){
        if (canFormKeyWithConcat(line.key, line.value)){
            result += line.key

        }
    }
    println(result)


}
fun canFormKeyWithConcat(key: Long, values: List<Int>): Boolean {
    fun check(current: Long, index: Int): Boolean {
        if (index == values.size) {
            return current == key
        }

        val nextValue = values[index].toLong()

        val concatenated = "$current$nextValue".toLong()

        return check(current + nextValue, index + 1) ||
                check(current * nextValue, index + 1) ||
                check(concatenated, index + 1)
    }

    return if (values.isNotEmpty()) check(values[0].toLong(), 1) else false
}

fun parser (path:String) : HashMap<Long, List<Int>>{

    val input = File(path).readLines()
    val dict = HashMap<Long, String>()
    for (line in input) {
        val arr = line.split(": ")
        dict[arr[0].toLong()] = arr[1]
    }

    val finalDict = HashMap<Long, List<Int>>()

    for (line in dict) {
        val arr = line.value.split(" ").map { it.toInt() }
        finalDict[line.key] = arr
    }
    return finalDict
}
