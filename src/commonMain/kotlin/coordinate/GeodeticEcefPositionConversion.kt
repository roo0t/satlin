package coordinate

import coordinate.ellipsoid.Wgs84Ellipsoid
import kotlin.math.*

// https://en.wikipedia.org/wiki/Geographic_coordinate_conversion#From_geodetic_to_ECEF_coordinates
fun GeodeticPosition.toEcefPosition(): EcefPosition {
    val ellipsoid = Wgs84Ellipsoid

    val a = ellipsoid.semiMajorAxis
    val b = ellipsoid.semiMinorAxis
    val f = ellipsoid.flattening

    val N_phi = a.pow(2) / sqrt(a.pow(2) * cos(latitude).pow(2) + b.pow(2) * sin(latitude).pow(2))
    val x = (N_phi + ellipsoidalHeight) * cos(latitude) * cos(longitude)
    val y = (N_phi + ellipsoidalHeight) * cos(latitude) * sin(longitude)
    val z = ((1 - f).pow(2) * N_phi + ellipsoidalHeight) * sin(latitude)

    return EcefPosition(x, y, z)
}

// https://en.wikipedia.org/wiki/Geographic_coordinate_conversion#From_ECEF_to_geodetic_coordinates
fun EcefPosition.toGeodeticPosition(): GeodeticPosition {
    val ellipsoid = Wgs84Ellipsoid

    val z_square = z.pow(2)

    val a = ellipsoid.semiMajorAxis
    val b = ellipsoid.semiMinorAxis
    val a_square = a.pow(2)
    val b_square = b.pow(2)
    val e_square = (a_square - b_square) / a_square
    val e_prime_square = (a_square - b_square) / b_square

    val p = sqrt(x.pow(2) + y.pow(2))
    val F = 54 * b_square * z_square
    val G = p.pow(2) + (1 - e_square) * z_square - e_square * (a_square - b_square)
    val c = e_square.pow(2) * F * p.pow(2) / G.pow(3)
    val s = (1 + c + sqrt(c.pow(2) + 2 * c)).pow(1.0 / 3.0)
    val k = s + 1 + 1 / s
    val P = F / (3 * k.pow(2) * G.pow(2))
    val Q = sqrt(1 + 2 * e_square.pow(2) * P)
    val r_0 = (-P * e_square * p) / (1 + Q) +
            sqrt(a_square / 2.0 * (1 + 1.0 / Q)
                    - (P * (1 - e_square) * z_square) / (Q * (1 + Q))
                    - P * p.pow(2) / 2.0)
    val U = sqrt((p - e_square * r_0).pow(2) + z_square)
    val V = sqrt((p - e_square * r_0).pow(2) + (1 - e_square) * z_square)
    val z_0 = b_square * z / a / V

    val ellipsoidalHeight = U * (1 - b_square / a / V)
    val latitude = atan((z + e_prime_square * z_0) / p)
    val longitude = atan2(y, x)

    return GeodeticPosition(latitude, longitude, ellipsoidalHeight)
}
