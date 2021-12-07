import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input.map { it.split(",") }.flatMap { it.map { it.toInt() } }

        val fuelList = mutableListOf<Int>()

        positions.forEach { pos ->
            var fuelForPos = 0
            positions.filterNot { it == pos }
                .forEach { fuelForPos += abs(pos - it) }
            fuelList.add(fuelForPos)
        }

        return fuelList.minOf { it }
    }

    fun part2(input: List<String>): Int {
        val positions = input.map { it.split(",") }.flatMap { it.map { it.toInt() } }

        val fuelList = mutableListOf<Int>()

        positions.forEach { pos ->
            var fuelForPos = 0
            positions.filterNot { it == pos }
                .forEach {
                    val n = abs(pos - it)
                    // Now with gaussian sum formula, to do
                    // 0 + 1 + 2 + ... + n
                    fuelForPos += ((n*(n+1))/2)
                }
            fuelList.add(fuelForPos)
        }

        return fuelList.minOf { it }
    }

    val demoInput = readInput("Day07Demo")
    println(part1(demoInput))
    println(part2(demoInput))

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
