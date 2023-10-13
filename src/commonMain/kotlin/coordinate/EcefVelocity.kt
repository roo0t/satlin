package coordinate

data class EcefVelocity(override val x: Meter, override val y: Meter, override val z: Meter) : EcefCoordinate {
}
