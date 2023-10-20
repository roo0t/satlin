package math

import coordinate.Radian
import kotlin.math.cos
import kotlin.math.sin

enum class RotationAxis {
    X_AXIS,
    Y_AXIS,
    Z_AXIS,
}

fun makeRotationMatrix(angle: Radian, rotationAxis: RotationAxis) =
    when (rotationAxis) {
        RotationAxis.X_AXIS -> Matrix(listOf(
            listOf(1.0, 0.0, 0.0),
            listOf(0.0, cos(angle), -sin(angle)),
            listOf(0.0, sin(angle), cos(angle)),
        ))
        RotationAxis.Y_AXIS -> Matrix(listOf(
            listOf(cos(angle), 0.0, sin(angle)),
            listOf(0.0, 1.0, 0.0),
            listOf(-sin(angle), 0.0, cos(angle)),
        ))
        RotationAxis.Z_AXIS -> Matrix(listOf(
            listOf(cos(angle), -sin(angle), 0.0),
            listOf(sin(angle), cos(angle), 0.0),
            listOf(0.0, 0.0, 1.0),
        ))
    }

fun rotateMatrix(matrix: Matrix, angle: Radian, rotationAxis: RotationAxis) =
    makeRotationMatrix(-angle, rotationAxis) * matrix
