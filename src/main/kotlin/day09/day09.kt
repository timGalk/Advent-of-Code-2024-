package day09

import Reader

val reader = Reader()
val input = reader.readString("src/main/resources/day09.txt").toList()
var transformed = transformToBlocks(input.map { it.toString().toInt() })


fun part01 (){


    val resultedList = sortedBlocks(transformed.toMutableList())
    var result = 0L
    for (i in 0 until resultedList.size){
        result += resultedList[i]*i
    }
    println(resultedList.size)
    println(result)


}
fun part02() {

}

fun transformToBlocks (list: List<Int>) :List<Int>{
    // let . in string will be a -1
    var result = mutableListOf<Int>()
    var current = 0
    for (i in 0 until list.size){

        if (i %2 == 0 ) {
            result.addAll(IntArray(list[i]){current}.toList())
            current ++

        }else {
            result.addAll(IntArray(list[i]){-1}.toList())
        }

    }
    return result
}
fun sortedBlocks (listofBlocks: MutableList<Int>) : List<Int> {

    var right = listofBlocks.size - 1
    var left = 0


    while (left < right) {
        if (listofBlocks[left] == -1) {
            if (listofBlocks[right] != -1) {
                     exchangeBlocks(listofBlocks, left, right)
                left++
                right--
            } else {
                right--
            }

        }
        else {
            left++
        }
    }

    return listofBlocks.filter { it != -1 }
}

fun exchangeBlocks(listOfBlocks: MutableList<Int>, index1: Int, index2: Int) {
    val temp = listOfBlocks[index1]
    listOfBlocks[index1] = listOfBlocks[index2]
    listOfBlocks[index2] = temp
}

 fun main() {
    part02()

}