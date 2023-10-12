package coordinate

import kotlin.math.abs
import kotlin.test.Test

class GeodeticEcefCoordinateConversionKtTest {
    private fun provideTestCases(): List<Pair<GeodeticCoordinate, EcefCoordinate>> {
        return listOf(
            // KAIST
            GeodeticCoordinate(36.373650.toRadian(), 127.362608.toRadian(), 55.08)
                    to EcefCoordinate(-3120195.27, 4086570.52, 3761687.39),
            // MIT
            GeodeticCoordinate(42.360300.toRadian(), 71.094163.toRadian(), -0.01)
                    to EcefCoordinate(1529386.08, 4465486.67, 4275260.81),
            // Antarctica
            GeodeticCoordinate(89.938246.toRadian(), 4.446291.toRadian(), 2937.12)
                    to EcefCoordinate(6879.95, 534.97, 6359685.72),
        )
    }

    @Test
    fun testGeodeticToEcefCoordinate() {
        provideTestCases().forEach {
            val actual = it.first.toEcefCoordinate()
            val expected = it.second
            val diff = (actual - expected).norm
            val threshold = 1e-2
            assert(diff < threshold) { "Difference between $actual and $expected is $diff >= $threshold" }
        }
    }

    @Test
    fun testEcefToGeodeticCoordinate() {
        provideTestCases().forEach {
            val actual = it.second.toGeodeticCoordinate()
            val expected = it.first

            val angleThreshold = 0.0001.toRadian()
            val heightThreshold = 0.01

            val latitudeDiff = abs(expected.latitude - actual.latitude)
            assert(latitudeDiff < angleThreshold) {
                "Latitude difference between ${actual.latitude.toDegree()}deg and ${expected.latitude.toDegree()}deg " +
                        "is ${latitudeDiff.toDegree()}deg >= ${angleThreshold.toDegree()}}"
            }
            val longitudeDiff = abs(expected.longitude - actual.longitude)
            assert(longitudeDiff < angleThreshold) {
                "Longitude difference between ${actual.longitude.toDegree()}deg and ${expected.longitude.toDegree()}deg " +
                        "is ${longitudeDiff.toDegree()}deg >= ${angleThreshold.toDegree()}}"
            }
            val heightDiff = abs(expected.ellipsoidalHeight - actual.ellipsoidalHeight)
            assert(heightDiff < heightThreshold) {
                "Height difference between ${actual.ellipsoidalHeight}m and ${expected.ellipsoidalHeight}m " +
                        "is ${heightDiff}m >= $heightThreshold"
            }
        }
    }

    @Test
    fun testEcefToGeodeticToEcefCoordinate() {
        provideTestCases().forEach {
            val actual = it.second.toGeodeticCoordinate().toEcefCoordinate()
            val expected = it.second
            val threshold = 1e-8
            val diff = (expected - actual).norm

            assert(diff < threshold) {
                "Difference between $actual and $expected is $diff >= $threshold"
            }
        }
    }
}
