package coordinate

data class EcefPosition(override val x: Meter, override val y: Meter, override val z: Meter) : EcefCoordinate {
    operator fun minus(rhs: EcefPosition): EcefPosition {
        return EcefPosition(rhs.x - x, rhs.y - y, rhs.z - z)
    }
}
