package coordinate

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class GeodeticToEcefCoordinateKtTest {
    @Test
    fun testGeodeticToEcefCoordinate() {
        assertTrue(ecefCoordinatesAreClose(
            EcefCoordinate(-3120195.0, 4086571.0, 3761687.0),
            GeodeticCoordinate(36.373650.toRadian(), 127.362608.toRadian(), 55.08).toEcefCoordinate(),
            1.0));
    }

    private fun ecefCoordinatesAreClose(a: EcefCoordinate, b: EcefCoordinate, threshold: Double): Boolean {
        return (b - a).norm < threshold
    }
}
