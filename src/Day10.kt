
fun main() {
    val closings = listOf(")", "]", "}", ">")

    fun getClosingCharFor(input: Char): Char {
        when (input) {
            '(' -> return ')'
            '[' -> return ']'
            '{' -> return '}'
            '<' -> return '>'
        }
        return ' '
    }

    fun getOpeningCharFor(input: Char): Char {
        when (input) {
            ')' -> return '('
            ']' -> return '['
            '}' -> return  '{'
            '>' -> return '<'
        }
        return ' '
    }

    fun isAnyClosing(input: Char): Boolean {
        if (getOpeningCharFor(input) != ' ') return true
        return false
    }

    fun hasNextAfterNext(i: MutableListIterator<Char>): Boolean {
        if (i.hasNext()) {
            i.next()
            if (i.hasNext()) {
                i.previous()
                return true
            }
        }
        return false
    }

    fun calculateScore(input: MutableList<Char>): Int {
        var score = 0
        input.forEach {
            when (it) {
                ')' -> score += 3
                ']' -> score += 57
                '}' -> score += 1197
                '>' -> score += 25137
            }
        }
        return score
    }

    fun getPart2Score(input: Char): Int {
        when (input) {
            ')' -> return 1
            ']' -> return 2
            '}' -> return 3
            '>' -> return 4
        }
        return 0
    }

    fun cleanedInput(input: List<String>): MutableList<String> {
        val cleanedInput = mutableListOf<String>()
        input.iterator().forEach {
            it.toString().apply {
                // Filter for all directly closed chunks (e.g. "()")
                val copy = it.toMutableList()
                var hasChanged = true
                while (hasChanged) {
                    hasChanged = false
                    val iterator = copy.listIterator()
                    while (hasNextAfterNext(iterator)) {
                        val next = iterator.next()
                        val nextAfterNext = iterator.next()
                        // Reset iterator by one, to point for "normal" next again
                        iterator.previous()
                        if (nextAfterNext == getClosingCharFor(next)) {
                            // In that case reset iterator one more and remove both
                            iterator.previous()
                            iterator.remove()
                            iterator.next()
                            iterator.remove()
                            hasChanged = true
                        }
                    }
                }
                //println(copy)
                cleanedInput.add(copy.toString())
            }
        }
        return cleanedInput
    }

    fun chars(input: List<String>): MutableList<Char> {
        val cleanedInput = cleanedInput(input)

        // {([(<{}[<>[]}>{[]{[(<()> (original)
        // {([(<[}>{{[(             (at that point)

        // Other example
        // [[<[([]))<([[{}[[()]]]
        // [[<[)<([

        // Another example:
        // [{[{({}]{}}([{[{{{}}([]
        // [{[{(]}([{[{([]

        val illegalChars = mutableListOf<Char>()
        // Find corrupted inputs
        cleanedInput.forEach {
            val copy = it.substring(1 until it.lastIndex).replace(", ", "").toMutableList()
            val iterator = copy.listIterator()
            while (iterator.hasNext()) {
                val next = iterator.next()
                // Check if current one is closing char
                if (isAnyClosing(next)) {
                    // check if that matches the last opening char
                    if (iterator.hasPrevious()) {
                        if (iterator.previous() == getOpeningCharFor(next)) {
                            // Valid
                            // TODO: some action required?
                        } else {
                            // Corrupt
                            illegalChars.add(next)
                            break
                        }
                        // Reset iterator
                        iterator.next()
                    }
                }
            }
        }
        return illegalChars
    }

    fun part1(input: List<String>): Int {
        val illegalChars = chars(input)

        return calculateScore(illegalChars)
    }

    fun part2(input: List<String>): Long {
        var cleaned = cleanedInput(input)

        val illegalChars = mutableListOf<Char>()
        val incomplete = mutableListOf<String>()
        // Find corrupted inputs
        cleaned = cleaned.map { it.substring(1 until it.lastIndex).replace(", ", "") }.toMutableList()
        val inputIterator = cleaned.listIterator()
        while (inputIterator.hasNext()) {
            val copy = inputIterator.next().toMutableList()
            val iterator = copy.listIterator()
            while (iterator.hasNext()) {

                val next = iterator.next()
                // Check if current one is closing char
                if (isAnyClosing(next)) {
                    // check if that matches the last opening char
                    if (iterator.hasPrevious()) {
                        if (iterator.previous() == getOpeningCharFor(next)) {
                            // Valid
                        } else {
                            // Corrupt
                            inputIterator.remove()
                            break
                        }
                        // Reset iterator
                        iterator.next()
                    }
                }
            }
        }

        val scoreStrings = mutableListOf<String>()
        // Reset iterator
        while (inputIterator.hasPrevious()) inputIterator.previous()

        while (inputIterator.hasNext()) {
            val next = inputIterator.next()
            var scoreString = ""

            for (i in next.lastIndex downTo 0) {
                if (!isAnyClosing(next[i])) scoreString += getClosingCharFor(next[i])
            }
            scoreStrings.add(scoreString)
        }

        val scores = mutableListOf<Long>()
        scoreStrings.forEach {
            var score = 0L
            it.forEach {
                score = score * 5 + getPart2Score(it)
            }
            scores.add(score)
        }

        scores.sort()

        return scores[scores.size / 2]
    }

    val demoInput = readInput("Day10Demo")
    println(part1(demoInput))
    assert(part1(demoInput) == 26397)
    println(part2(demoInput))

    val input = readInput("Day10")
    println(part1(input))
    assert(part1(input) == 462693)
    println(part2(input))
}