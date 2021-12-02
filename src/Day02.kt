enum class Command {
    FORWARD, DOWN, UP
}

fun main() {
    fun part1(input: List<String>): Int {
        var horizontalPos = 0
        var depth = 0

        input.map { it.split(" ") }.forEach {
            when (Command.valueOf(it[0].uppercase())) {
                Command.FORWARD -> horizontalPos += it[1].toInt()
                Command.DOWN -> depth += it[1].toInt()
                Command.UP -> depth -= it[1].toInt()
            }
        }

        return horizontalPos * depth
    }

    fun part2(input: List<String>): Int {
        var horizontalPos = 0
        var depth = 0
        var aim = 0

        input.map { it.split(" ") }.forEach {
            when (Command.valueOf(it[0].uppercase())) {
                Command.FORWARD -> {
                    horizontalPos += it[1].toInt()
                    depth += aim * it[1].toInt()
                }
                Command.DOWN -> aim += it[1].toInt()
                Command.UP -> aim -= it[1].toInt()
            }
        }

        return horizontalPos * depth
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
