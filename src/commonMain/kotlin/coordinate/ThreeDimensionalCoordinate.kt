package coordinate

import math.Matrix
import kotlin.math.pow
import kotlin.math.sqrt

interface ThreeDimensionalCoordinate {
    val x: Double
    val y: Double
    val z: Double

    val norm: Double
        get() = sqrt(x.pow(2) + y.pow(2) + z.pow(2))

    fun toColumnMatrix() = Matrix(3, 1, listOf(x, y, z).toMutableList())
}
