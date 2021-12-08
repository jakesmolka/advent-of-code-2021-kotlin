
fun main() {
    fun part1(input: List<String>): Int {
        val outputs = input.map { it.split(" | ").last() }.map {
            it.split(" ").map { it.count() }
        }

        var count = 0
        outputs.forEach {
            it.forEach {
                when (it) {
                    2 -> count++
                    3 -> count++
                    4 -> count++
                    7 -> count++
                }
            }
        }

        return count
    }

    fun processEntry(entry: EntryLine): Int {
        val display = DisplayNumbers()
        val segments = DisplaySegments()

        // 1. Get unique numbers
        display.one = entry.input.find { it.count() == 2 }.toString()
        display.four = entry.input.find { it.count() == 4 }.toString()
        display.seven = entry.input.find { it.count() == 3 }.toString()
        display.eight = entry.input.find { it.count() == 7 }.toString()

        // 2. Get segment 6
        segments.six = display.seven.filterNot { seven -> display.one.any { it == seven } }.toCharArray().first()

        // 3. Get number 6 and seg 2 and 5
        entry.input.filter { it.count() == 6 }

        return 1
    }

    fun part2(input: List<String>): Int {
        val entries = input.map { it.split(" | ") }.map {
            EntryLine(it.first().split(" "), it.last().split(" ")) }

        var count = 0
        for (entry in entries) count += processEntry(entry)

        return 1
    }

    val demoInput = readInput("Day08Demo")
    println(part1(demoInput))
    assert(part1(demoInput) == 26)
    println(part2(demoInput))

    val input = readInput("Day08")
    println(part1(input))
    assert(part1(input) == 352)
    println(part2(input))
}

data class EntryLine(val input: List<String>, val output: List<String>)

data class DisplayNumbers(var zero: String = "", var one: String = "", var two: String = "",
                          var three: String = "", var four: String = "", var five: String = "", val six: String = "",
                          var seven: String = "", var eight: String = "", var nine: String = "")

/* Coding of segments
 666
4   5
4   5
 333
1   2
1   2
 000
 */
data class DisplaySegments(var zero: Char = ' ', var one: Char = ' ', var two: Char = ' ',
var three: Char = ' ', var four: Char = ' ', var five: Char = ' ',
var six: Char = ' ')