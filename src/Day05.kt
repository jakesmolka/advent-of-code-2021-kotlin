data class Line(val fromPos: Pair<Int, Int>,    // x1, y1
                val toPos: Pair<Int, Int>)      // x2 ,y2

fun main() {
    fun parseInput(input: List<String>, output: MutableList<Line>) {
        input.map { it.split(" -> ") }
            .map { it.map {
                it.split(",")
                }
                .map { listOf(Pair(it[0].toInt(), it[1].toInt())) }.let {
                    output.add(Line(it[0].first(), it[1].first()))
                }
            }
    }

    fun populateDensityHoriAndVerti(diagram: Array<Array<Int>>, lines: List<Line>) {
        lines.forEach {
            val fromX: Int
            val toX: Int
            val fromY: Int
            val toY: Int

            // Normalize direction
            if (it.fromPos.first <= it.toPos.first) {
                fromX = it.fromPos.first
                toX = it.toPos.first
            } else {
                fromX = it.toPos.first
                toX = it.fromPos.first
            }
            if (it.fromPos.second <= it.toPos.second) {
                fromY = it.fromPos.second
                toY = it.toPos.second
            } else {
                fromY = it.toPos.second
                toY = it.fromPos.second
            }

            // Populate density diagram
            if (fromX == toX) {
                // Vertical line. Do for the length of the line:
                for (i: Int in fromY .. toY) {
                    diagram[i][fromX] += 1
                }
            } else if (fromY == toY) {
                // Horizontal line. Do for the length of the line:
                for (i: Int in fromX .. toX) {
                    diagram[fromY][i] += 1
                }
            }
        }
    }

    /*
    WIP!
     */
    fun populateDensityDiagonals(diagram: Array<Array<Int>>, lines: List<Line>) {
        // 1,5 -> 0,5
        // has do be normalized into
        // 0,5 -> 1,5

        lines.forEach {
            var fromX = it.fromPos.first
            var toX = it.toPos.first
            var fromY = it.fromPos.second
            var toY = it.toPos.second

            // If non-normalized direction
            if (it.fromPos.first > it.toPos.first || it.fromPos.second > it.toPos.second) {
                // Switch "from" and "to"
                fromX = it.toPos.first
                toX = it.fromPos.first
                fromY = it.toPos.second
                toY = it.fromPos.second
            }

            var x = fromX
            var y = fromY
            if (fromX > toX) {
                // "\" type
                for (ix: Int in toX .. fromX) {
                    while (y != toY) {
                        diagram[x][y] += 1
                        x += 1
                        y += 1
                    }
                }
            } else {
                // "/" type
                for (ix: Int in fromX .. toX) {
                    while (y != toY) {
                        diagram[x][y] += 1
                        x += 1
                        y -= 1
                    }
                }
            }
        }
    }

    fun demo(input: List<String>): Int {
        // 1000 x 1000 diagram to represent line density
        val diagram = Array(10) { Array(10) {0} }

        // Parse input
        val lineList = mutableListOf<Line>()
        parseInput(input, lineList)

        // Filter lines for only horizontal and vertical lines
        val filteredLines = lineList.filter { it.fromPos.first == it.toPos.first || it.fromPos.second == it.toPos.second }

        // Process: populate line density on diagram
        populateDensityHoriAndVerti(diagram, filteredLines)

        val highDensityDots = diagram.flatMap { it.map { it } }.filter { it > 1 }

        return highDensityDots.size
    }

    fun demo2(input: List<String>): Int {
        // 1000 x 1000 diagram to represent line density
        val diagram = Array(10) { Array(10) {0} }

        // Parse input
        val lineList = mutableListOf<Line>()
        parseInput(input, lineList)

        // Filter lines for only horizontal and vertical lines
        val filteredLines = lineList.filter { it.fromPos.first == it.toPos.first || it.fromPos.second == it.toPos.second }

        // Process: populate line density on diagram
        populateDensityHoriAndVerti(diagram, filteredLines)

        val onlyDiagonals = lineList.filterNot { it.fromPos.first == it.toPos.first || it.fromPos.second == it.toPos.second }
        populateDensityDiagonals(diagram, onlyDiagonals)

        val highDensityDots = diagram.flatMap { it.map { it } }.filter { it > 1 }

        return highDensityDots.size
    }

    fun part1(input: List<String>): Int {
        // 1000 x 1000 diagram to represent line density
        val diagram = Array(1000) { Array(1000) {0} }

        // Parse input
        val lineList = mutableListOf<Line>()
        parseInput(input, lineList)

        // Filter lines for only horizontal and vertical lines
        val filteredLines = lineList.filter { it.fromPos.first == it.toPos.first || it.fromPos.second == it.toPos.second }

        // Process: populate line density on diagram
        populateDensityHoriAndVerti(diagram, filteredLines)

        val highDensityDots = diagram.flatMap { it.map { it } }.filter { it > 1 }

        return highDensityDots.size
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val demo = readInput("Day05Demo")
    println(demo(demo))
    println(demo2(demo))

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
