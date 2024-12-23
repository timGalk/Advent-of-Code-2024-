fun main(){
    val file = "src/main/resources/input.txt"
    val reader = Reader()
    val array = reader.readFileToNestedArray(file)
    var left = arrayOf<Int>()
    var right = arrayOf<Int>()

    for (elem in array) {
        left = left.plus(elem[0])
        right = right.plus(elem[1])
    }


    val map = mutableMapOf<Int, Int>()

    for (number in right) {
        map[number] = map.getOrDefault(number, 0) + 1
    }

    var sumOfMulti = 0
    for (number in left) {
        if (map.containsKey(number)) {
            sumOfMulti += number * (map[number] ?: 0)
        }
    }

    println(sumOfMulti)


}