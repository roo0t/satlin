package coordinate

import coordinate.ellipsoid.Wgs84Ellipsoid
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

// https://en.wikipedia.org/wiki/Geographic_coordinate_conversion#From_geodetic_to_ECEF_coordinates
fun GeodeticCoordinate.toEcefCoordinate(): EcefCoordinate {

    val ellipsoid = Wgs84Ellipsoid

    val a = ellipsoid.semiMajorAxis
    val b = ellipsoid.semiMinorAxis
    val f = ellipsoid.flattening

    val N_phi = a.pow(2) / sqrt(a.pow(2) * cos(latitude).pow(2) + b.pow(2) * sin(latitude).pow(2))
    val x = (N_phi + ellipsoidalHeight) * cos(latitude) * cos(longitude)
    val y = (N_phi + ellipsoidalHeight) * cos(latitude) * sin(longitude)
    val z = ((1 - f).pow(2) * N_phi + ellipsoidalHeight) * sin(latitude)

    return EcefCoordinate(x, y, z)
}
