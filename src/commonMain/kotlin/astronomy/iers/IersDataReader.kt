package astronomy.iers

object IersDataReader {
    fun readIersDataRows(iersData: String) = iersData.lines().mapNotNull {
        try {
            readIersDataRow(it)
        } catch (e: NumberFormatException) {
            null
        }
    }.toList()

    private fun readIersDataRow(line: String) = IersDataRow(
        line.substring(0, 2).trim().toInt(),
        line.substring(2, 4).trim().toInt(),
        line.substring(4, 6).trim().toInt(),
        line.substring(7, 15).trim().toDouble(),
        line.substring(16, 17) == "P",
        line.substring(18, 27).trim().toDouble(),
        line.substring(27, 36).trim().toDouble(),
        line.substring(37, 46).trim().toDouble(),
        line.substring(46, 55).trim().toDouble(),
        line.substring(57, 58) == "P",
        line.substring(58, 68).trim().toDouble(),
        line.substring(68, 78).trim().toDouble(),
        line.substring(79, 86).run { if (isBlank()) null else trim().toDouble() },
        line.substring(86, 93).run { if (isBlank()) null else trim().toDouble() },
        line.substring(95, 96) == "P",
        line.substring(97, 106).trim().toDouble(),
        line.substring(106, 115).trim().toDouble(),
        line.substring(116, 125).trim().toDouble(),
        line.substring(125, 134).trim().toDouble(),
        line.substring(134, 144).trim().toDouble(),
        line.substring(144, 154).trim().toDouble(),
        line.substring(154, 165).trim().toDouble(),
        line.substring(165, 175).trim().toDouble(),
        line.substring(175, 185).trim().toDouble(),
    )
}
