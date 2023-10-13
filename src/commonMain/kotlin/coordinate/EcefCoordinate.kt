package coordinate

import kotlin.math.pow
import kotlin.math.sqrt

data class EcefCoordinate(val x: Meter, val y: Meter, val z: Meter) {
    val norm: Double
        get() = sqrt(x.pow(2) + y.pow(2) + z.pow(2))

    operator fun minus(rhs: EcefCoordinate): EcefCoordinate {
        return EcefCoordinate(rhs.x - x, rhs.y - y, rhs.z - z)
    }
}
