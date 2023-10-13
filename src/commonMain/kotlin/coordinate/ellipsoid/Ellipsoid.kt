package coordinate.ellipsoid

import coordinate.Meter

interface Ellipsoid {
    val name: String
    val semiMajorAxis: Meter
    val inverseFlattening: Double

    val semiMinorAxis: Meter
        get() = semiMajorAxis - (semiMajorAxis * flattening)
    val flattening: Double
        get() = 1 / inverseFlattening
}
