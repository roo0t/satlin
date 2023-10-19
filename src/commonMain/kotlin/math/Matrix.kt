package math

import kotlin.math.pow
import kotlin.math.sqrt

class Matrix private constructor(val rowCount: Int, val columnCount: Int, private val values: MutableList<Double>) {
    init {
        require(rowCount > 0) {
            "rowCount must be positive"
        }
        require(columnCount > 0) {
            "columnCount must be positive"
        }
        require(rowCount * columnCount == values.size) {
            "values must have the same size with rowCount * columnCount"
        }
    }

    constructor(rowCount: Int, columnCount: Int, initialValue: Double = 0.0) : this(
        rowCount,
        columnCount,
        ArrayList((0..<(rowCount * columnCount)).map { initialValue }.toMutableList())
    )

    constructor(rowCount: Int, columnCount: Int, valueGenerator: (Int, Int) -> Double) : this(
        rowCount,
        columnCount,
        (0..<rowCount).flatMap { row ->
            (0..<columnCount).map { colum -> valueGenerator(row, colum) }
        }.toMutableList()
    )

    constructor(values: List<List<Double>>) : this(
        values.size,
        values.firstOrNull()?.size ?: 0,
        values.flatten().toMutableList()
    ) {
        require(values.map { it.size }.distinct().size <= 1) {
            "all rows must have the same number of columns"
        }
    }

    private fun validateIndex(row: Int, column: Int) {
        require(row in 0..<rowCount) {
            "row must be between 0 and rowCount"
        }
        require(column in 0..<columnCount) {
            "column msut be between 0 and columnCount"
        }
    }

    private fun toFlatIndex(row: Int, column: Int): Int {
        validateIndex(row, column)
        return row * columnCount + column
    }

    operator fun get(row: Int, column: Int): Double = values[toFlatIndex(row, column)]
    operator fun set(row: Int, column: Int, value: Double) {
        values[toFlatIndex(row, column)] = value
    }

    fun row(index: Int) = object : DoubleIterator() {
        var column = 0
        override fun hasNext() = column < columnCount
        override fun nextDouble() = get(index, column++)
    }

    fun column(index: Int) = object : DoubleIterator() {
        var row = 0
        override fun hasNext() = row < rowCount
        override fun nextDouble() = get(row++, index)
    }

    operator fun plus(rhs: Matrix): Matrix {
        require(rowCount == rhs.rowCount) {
            "rowCounts must agree"
        }
        require(columnCount == rhs.columnCount) {
            "columnCounts must agree"
        }
        return Matrix(rowCount, columnCount) { row, column ->
            get(row, column) + rhs[row, column]
        }
    }

    operator fun minus(rhs: Matrix): Matrix {
        require(rowCount == rhs.rowCount) {
            "rowCounts must agree"
        }
        require(columnCount == rhs.columnCount) {
            "columnCounts must agree"
        }
        return Matrix(rowCount, columnCount) { row, column ->
            get(row, column) - rhs[row, column]
        }
    }

    operator fun unaryMinus() = Matrix(rowCount, columnCount) { row, column -> -get(row, column) }

    operator fun times(rhs: Matrix): Matrix {
        require(columnCount == rhs.rowCount) {
            "columnCount and rhs.rowCount must agree"
        }
        return Matrix(rowCount, rhs.columnCount) { row, column ->
            (0..<columnCount).sumOf { get(row, it) * rhs[it, column] }
        }
    }

    fun frobeniusNorm() = sqrt(values.sumOf { it.pow(2) })
}
