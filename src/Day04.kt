data class Entry(val value: String, var isMarked: Boolean)

data class Board(
    val entries: MutableList<MutableList<Entry>>,
    val horizontalScores: Array<Int>,
    val verticalScores: Array<Int>,
    var isWon: Boolean) // for Part 2 only

fun main() {
    fun parseBoardInputToObjects(boardInput: List<String>, boards: MutableList<Board>) {
        // Remove empty lines
        boardInput.filterNot { it.isEmpty() }
            // Get each 5-rows set (one bingo board)
            .windowed(5, 5)
            .mapIndexed { index, list ->
                // For each raw board data set, first add new board and sanitize input
                boards.add(Board(
                    mutableListOf(),
                    arrayOf(0, 0, 0, 0, 0),
                    arrayOf(0, 0, 0, 0, 0),
                    false
                ))
                list.map {
                    it.replace("  ", " ")
                }.map { it.trim() }.map {
                    it.split(" ")
                }
                    .map {
                        // Parse data as Entry in rows
                        val entries = mutableListOf<Entry>()
                        it.mapTo(entries) { Entry(it, false) }
                    }
                    .mapIndexed { indexLine, mutableList ->
                        // For each row add Entry to matching 2D position on board
                        boards[index].entries.add(indexLine, mutableListOf())
                        mutableList.withIndex().forEach {
                            //boards[index].entries[indexLine][it.index] = it.value
                            boards[index].entries[indexLine].add(it.index, it.value)
                        }
                    }
            }
    }

    /**
     * Searches for match on Board. Marks if found. Returns true if that made a win.
     * (For Part 1)
     */
    fun markEntryIfMatches(board: Board, value: String): Boolean {
        board.entries.forEachIndexed { horizontalIndex, rowList ->
            rowList.forEachIndexed { verticalIndex, entry ->
                if (entry.value == value) {
                    entry.isMarked = true
                    board.horizontalScores[verticalIndex]++
                    board.verticalScores[horizontalIndex]++
                    if (board.horizontalScores[verticalIndex] == 5 || board.verticalScores[horizontalIndex] == 5)
                        return true
                }
            }
        }

        return false
    }

    /**
     * Searches for match on Board. Marks if found. Adds board with win to won list.
     * (For Part 2)
     */
    fun markEntryIfMatchesAndAddBoardToWonListIfWon(
            board: Board,
            value: String,
            wonBoards: MutableList<Board>,
            boardToNumberMapList: MutableList<Pair<Board, String>>): Boolean {
        board.entries.forEachIndexed { horizontalIndex, rowList ->
            rowList.forEachIndexed { verticalIndex, entry ->
                if (entry.value == value) {
                    entry.isMarked = true
                    board.horizontalScores[horizontalIndex]++
                    board.verticalScores[verticalIndex]++
                    if (board.horizontalScores[horizontalIndex] == 5 || board.verticalScores[verticalIndex] == 5) {
                        wonBoards.add(board)
                        boardToNumberMapList.add(Pair(board, value))
                        board.isWon = true
                        return true // to break
                    }
                }
            }
        }
        return false
    }

    fun sumOfUnmarkedNumbers(board: Board): Int {
        return board.entries.flatMap { it.filterNot { it.isMarked } }.sumOf { it.value.toInt() }
    }

    fun part1(input: List<String>): Int {
        // Read first line as "draw numbers input"
        val drawNumbers = input[0].split(",").map { it.trim() }

        // Parse boards, by also skipping draw numbers and empty line
        val boards = mutableListOf<Board>()
        val boardInput = input.subList(2, input.size)
        parseBoardInputToObjects(boardInput, boards)

        // Go through draw numbers and mark matching entries on boards
        for (number in drawNumbers) {
            for (board in boards) {
                // Mark matches and also return true if matched
                if (markEntryIfMatches(board, number)) {
                    return sumOfUnmarkedNumbers(board) * number.toInt()
                }
            }
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        // Read first line as "draw numbers input"
        val drawNumbers = input[0].split(",").map { it.trim() }

        // Parse boards, by also skipping draw numbers and empty line
        val boards = mutableListOf<Board>()
        val boardInput = input.subList(2, input.size)
        parseBoardInputToObjects(boardInput, boards)

        // Go through draw numbers and mark matching entries on boards
        val wonList = mutableListOf<Board>()
        //val boardToNumberMap = mutableMapOf<Board, String>()
        val boardToNumberList = mutableListOf<Pair<Board, String>>()
        for (number in drawNumbers) {
            for (board in boards) {
                // If board not already won...
                if (!board.isWon) {
                    // Mark matches and set data list and map
                    markEntryIfMatchesAndAddBoardToWonListIfWon(board, number, wonList, boardToNumberList)
                }
            }
        }

        val wonNumber = boardToNumberList.first { it.first == wonList.last() }.second.toInt()
        return sumOfUnmarkedNumbers(wonList.last()) * wonNumber
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
