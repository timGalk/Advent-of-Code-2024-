package day09

import Reader
import java.util.*

val reader = Reader()
val input = (reader.readString("src/main/resources/day09.txt").toList())
    .map { it.toString().toInt() }
var transformed = transformToBlocks(input)


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
    val disc = input.toMutableList()
    println(getPart2Result(disc))
}

fun getPart2Result(disc: MutableList<Int>): Long {
    data class FileSection(var id: Int, var firstIndex: Int, var length: Int)

    val fileSections = mutableListOf<FileSection>()
    val gapIndexMap = mutableMapOf<Int, PriorityQueue<Int>>()
    (1..9).forEach { gapIndexMap[it] = PriorityQueue() }

    var fileId = 0
    var isFile = true
    var currentIndex = 0
    for (sectionLength in disc) {
        if (isFile) {
            fileSections.add(FileSection(fileId, currentIndex, sectionLength))
            fileId++
        } else if (sectionLength != 0) {
            gapIndexMap[sectionLength]!!.add(currentIndex)
        }
        currentIndex += sectionLength
        isFile = !isFile
    }

    while (fileId > 0) {
        fileId--
        val fileIndex = fileSections[fileId].firstIndex
        val fileLength = fileSections[fileId].length

        val bestGap = gapIndexMap
            .filter { entry -> entry.key >= fileLength }
            .filter { entry -> entry.value.isNotEmpty() }
            .map { entry -> entry.key to entry.value }
            .minByOrNull { entry -> entry.second.peek() }

        if (bestGap == null) continue
        val gapLength = bestGap.first
        val gapIndex = bestGap.second.peek()
        if (gapIndex > fileIndex) continue
        bestGap.second.poll()

        fileSections[fileId].apply { firstIndex = gapIndex }

        val remainingGap = gapLength - fileLength
        if (remainingGap > 0) {
            val newGapIndex = gapIndex + fileLength
            gapIndexMap[remainingGap]!!.add(newGapIndex)
        }
    }

    return fileSections.sumOf { f ->
        (0 until f.length).sumOf { index ->
            (f.id.toLong() * (f.firstIndex + index))
        }
    }
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