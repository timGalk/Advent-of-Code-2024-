/**
 * The main function reads an input file, processes the data into two arrays,
 * calculates the sum of absolute differences between corresponding elements,
 * and prints the result.
 */
fun main() {
    // Path to the input file
    val file = "src/main/resources/input.txt"

    // Create an instance of the Reader class
    val reader = Reader()

    // Read the file and convert it to a nested array
    val array = reader.readFileToNestedArray(file)

    // Initialize empty arrays for 'left' and 'right'
    var left = arrayOf<Int>()
    var right = arrayOf<Int>()

    // Populate 'left' and 'right' arrays with elements from the nested array
    for (elem in array) {
        left = left.plus(elem[0]) // Add the first element to the 'left' array
        right = right.plus(elem[1]) // Add the second element to the 'right' array
    }

    // Initialize sum to 0
    var sum = 0

    // Calculate the sum of absolute differences between corresponding elements
    for (i in 0 until left.size) {
        sum += Math.abs(left[i] - right[i])
    }

    // Print the result
    println(sum)
}