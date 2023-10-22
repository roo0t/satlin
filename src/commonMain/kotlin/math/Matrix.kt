package math

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class Matrix constructor(val rowCount: Int, val columnCount: Int, private val values: MutableList<Double>) {
    companion object {
        fun identity(n: Int) = Matrix(n, n) { row, column -> if (row == column) 1.0 else 0.0 }
    }

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

    fun inverseTimes(vector: Matrix): Matrix {
        return doGaussianElimination(vector)
    }

    private fun doGaussianElimination(vector: Matrix): Matrix {
        require(vector.rowCount == rowCount) {
            "vector and matrix must have the same number of rows"
        }
        require(vector.columnCount == 1) {
            "vector must be a single-column matrix"
        }

        val clone = Matrix(rowCount, columnCount, values.toMutableList())
        val result = Matrix(vector.rowCount, vector.columnCount, vector.values.toMutableList())
        for (i in 0..<rowCount) {
            val rowIndexWithNonzeroLeadingColumn = (i..<rowCount).firstOrNull { abs(clone[it, i]) > 1e-6 }
            if (rowIndexWithNonzeroLeadingColumn == null) {
                continue
            } else {
                clone.exchangeRows(i, rowIndexWithNonzeroLeadingColumn)
                result.exchangeRows(i, rowIndexWithNonzeroLeadingColumn)
            }

            for (j in (i + 1)..<rowCount) {
                val factor = clone[j, i] / clone[i, i]
                for (k in i..<columnCount) {
                    clone[j, k] -= clone[i, k] * factor
                }
                result[j, 0] -= result[i, 0] * factor
            }
            for (k in (i + 1)..<columnCount) {
                clone[i, k] /= clone[i, i]
            }
            result[i, 0] /= clone[i, i]
            clone[i, i] = 1.0
        }

        for (i in (0..<rowCount).reversed()) {
            for (j in 0..<i) {
                result[j, 0] -= result[i, 0] * clone[j, i]
            }
        }
        return result
    }

    private fun exchangeRows(i: Int, j: Int) {
        require(i in 0..<rowCount)
        require(j in 0..<rowCount)
        if (i == j) {
            return
        }

        for (k in 0..<columnCount) {
            val temp = this[i, k]
            this[i, k] = this[j, k]
            this[j, k] = temp
        }
    }
}
