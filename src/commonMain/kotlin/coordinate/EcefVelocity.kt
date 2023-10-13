package coordinate

data class EcefVelocity(override val x: MeterPerSec, override val y: MeterPerSec, override val z: MeterPerSec) : EcefCoordinate {
}
