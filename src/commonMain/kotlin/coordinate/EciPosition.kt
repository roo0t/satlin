package coordinate

import math.Matrix
import time.Ut1Instant

data class EciPosition(
    override val x: Meter,
    override val y: Meter,
    override val z: Meter,
    override val referenceTime: Ut1Instant
) : EciCoordinate {
    constructor(columnMatrix: Matrix, referenceTime: Ut1Instant) : this(
        columnMatrix[0, 0],
        columnMatrix[1, 0],
        columnMatrix[2, 0],
        referenceTime
    ) {
        require(columnMatrix.rowCount == 3 && columnMatrix.columnCount == 1) {
            "provided matrix is not of size 3 x 1"
        }
    }
}
