package astronomy.iau2006

import coordinate.Radian
import longdouble.LongDouble
import math.Matrix
import math.RotationAxis
import math.rotateMatrix

// ported from iauPom00
fun makePolarMotionMatrix(xp: Radian, yp: Radian, tioLocator: LongDouble): Matrix {
    var r = Matrix.identity(3)
    r = rotateMatrix(r, tioLocator.toDouble(), RotationAxis.Z_AXIS)
    r = rotateMatrix(r, -xp, RotationAxis.Y_AXIS)
    r = rotateMatrix(r, -yp, RotationAxis.X_AXIS)
    return r
}
