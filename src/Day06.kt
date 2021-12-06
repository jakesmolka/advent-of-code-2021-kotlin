fun main() {
    fun process(input: List<String>, days: Int): Int {
        val initialFishes = input.flatMap { it.split(",") }.map { it.toInt() }
        val fishCounterList = initialFishes.toMutableList()

        for (i in 1 .. days) {
            val iterator = fishCounterList.listIterator()
            var newFishes = 0;
            while (iterator.hasNext()) {
                val next = iterator.next()
                if (next != 0) {
                    iterator.set(next.minus(1))
                } else {
                    iterator.set(6)
                    newFishes++
                }
                // and ignore "8"
            }

            for (n in 1 .. newFishes) {
                fishCounterList.add(8)
            }
        }

        return fishCounterList.count()
    }

    fun processOptimized(input: List<String>, days: Int): Long {
        val initialFishes = input.flatMap { it.split(",") }.map { it.toInt() }

        val fishCounter = LongArray(9)
        initialFishes.forEach { fishCounter[it]++ }

        for (i in 1 .. days) {

            val newFishes = fishCounter[0]

            for (index in 1 .. 8) {
                fishCounter[index.minus(1)] = fishCounter[index]
            }

            // Add the timer which got reset to 6
            fishCounter[6] += newFishes
            // Add new fishes
            fishCounter[8] = newFishes
        }

        return fishCounter.sum()
    }

    fun part1(input: List<String>): Int {
        return process(input, 80)
    }

    fun part2(input: List<String>): Long {
        return processOptimized(input, 256)
    }

    val demoInput = readInput("Day06Demo")
    println(part1(demoInput))
    println(processOptimized(demoInput, 80)) // Test optimized function

    val input = readInput("Day06")
    println(part1(input))
    println(processOptimized(input, 80)) // Test optimized function
    println(part2(input))
}
