package coordinate

import math.Matrix
import time.Ut1Instant

data class EcefPosition(override val x: Meter, override val y: Meter, override val z: Meter) : EcefCoordinate {
    constructor(columnMatrix: Matrix) : this(columnMatrix[0, 0], columnMatrix[1, 0], columnMatrix[2, 0]) {
        require(columnMatrix.rowCount == 3 && columnMatrix.columnCount == 1) {
            "provided matrix is not of size 3 x 1"
        }
    }

    operator fun minus(rhs: EcefPosition): EcefPosition {
        return EcefPosition(rhs.x - x, rhs.y - y, rhs.z - z)
    }
}
