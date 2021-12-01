fun main() {
    fun countComparison(numbers: List<Int>): Int {
        val iterator = numbers.listIterator()

        var counter = 0
        while (iterator.hasNext()) {
            if (iterator.hasPrevious() &&
                (numbers[iterator.previousIndex()] < numbers[iterator.nextIndex()]))
                counter++
            iterator.next()
        }

        return counter
    }

    fun part1(input: List<String>): Int {
        val numbers = input.map { it.toInt() }

        return countComparison(numbers)
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toInt() }

        val windows = numbers.windowed(3) { it.sum() }

        return countComparison(windows)
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
