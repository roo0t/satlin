package coordinate.ellipsoid

import coordinate.Meter

object Wgs84Ellipsoid : Ellipsoid {
    override val name: String
        get() = "WGS 84"
    override val semiMajorAxis: Meter
        get() = 6378137.0
    override val inverseFlattening: Double
        get() = 298.257223563
}
