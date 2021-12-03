fun main() {
    fun part1(input: List<String>): Int {
        // Save count of ones ("1") for each of the 12 positions
        val countOfOnes = IntArray(12)

        // Access each row with a 12 bit entry and go over each bit
        input.map { it.chunked(1) }.map {
            val iterator = it.listIterator()
            while (iterator.hasNext()) {
                // Add "1" or "0" to counting array, at given index 0-11 (12 indices)
                countOfOnes[iterator.nextIndex()] += iterator.next().toInt()
            }
        }

        // Most common bit per position
        var gammaBinaryString = ""
        // Opposite
        var epsilonBinaryString = ""
        for (count in countOfOnes) {
            // If more "1"s on that position
            if (count > (input.size / 2)) {
                gammaBinaryString += "1"
                epsilonBinaryString += "0"
            } else {
                gammaBinaryString += "0"
                epsilonBinaryString += "1"
            }
        }

        return gammaBinaryString.toInt(2) * epsilonBinaryString.toInt(2)
    }

    fun part2(input: List<String>): Int {
        // Save count of ones ("1") for each of the 12 positions
        val countOfOnes = IntArray(12)
        var size = 0

        // Access each row with a 12 bit entry and go over each bit
        input.map { it.chunked(1) }.map {
            val iterator = it.listIterator()
            while (iterator.hasNext()) {
                // Add "1" or "0" to counting array, at given index 0-11 (12 indices)
                countOfOnes[iterator.nextIndex()] += iterator.next().toInt()
            }
            size++
        }
        // countOfOnes now has the count of ones for each bit/position

        var oxygen = input.toList()
        for (i in 0..11) {
            val count = oxygen.filter { it[i] == '1' }.count()

            if (count >= oxygen.size/2) {
                oxygen = oxygen.filter { it[i] == '1' }
            } else {
                oxygen = oxygen.filter { it[i] == '0' }
            }

            if (oxygen.size == 1) break
        }

        var co2 = input.toList()
        for (i in 0..11) {
            val count = co2.filter { it[i] == '1' }.count()

            if (count >= co2.size/2) {
                co2 = co2.filter { it[i] == '0' }
            } else {
                co2 = co2.filter { it[i] == '1' }
            }

            if (co2.size == 1) break
        }

        return oxygen[0].toInt(2) * co2[0].toInt(2)
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
