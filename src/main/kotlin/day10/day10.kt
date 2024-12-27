package  day10

import Reader
val reader = Reader()
val path = "src/main/resources/day10.txt"
val example = "src/main/resources/exampleday10.txt"

val map = reader.readFileToNestedCharArray(path)


 fun main (){
    println(map)
 }

