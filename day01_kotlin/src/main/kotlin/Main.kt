import java.io.File

const val INPUT = "input.txt";

fun main() {
    var total = 0
    File(INPUT).forEachLine {
        val (first, last) = getFirstAndLastNum(it)
        total += "$first$last".toInt()
        println("calibration value for line $it: $first$last")
    }
    println("\ntotal calibration value: $total")
}

fun getFirstAndLastNum(line: String): Pair<Int, Int> {
    var first: Int? = null
    var last: Int? = null
    // simply replace the words with numbers
    val line: String = line.replace("one", "one1one")
        .replace("two", "two2two")
        .replace("three", "three3three")
        .replace("four", "four4four")
        .replace("five", "five5five")
        .replace("six", "six6six")
        .replace("seven", "seven7seven")
        .replace("eight", "eight8eight")
        .replace("nine", "nine9nine")
    for (c in line.toCharArray().iterator()) {
        if (c in '0'..'9') {
            if (first == null) {
                first = c.digitToInt()
            }
            last = c.digitToInt()
        }
    }
    if (first == null || last == null) {
        throw Exception("Could not find first and last number in line: $line")
    }
    return Pair(first, last)
}