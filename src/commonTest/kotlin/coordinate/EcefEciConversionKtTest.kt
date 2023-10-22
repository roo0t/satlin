package coordinate

import astronomy.J2000Constants
import astronomy.iers.IersData
import time.convertUtcToUt1
import kotlin.test.Test
import kotlin.test.assertEquals

class EcefEciConversionKtTest {
    @Test
    fun eciToEcefConversionTest() {
        val utcJd = J2000Constants.epoch.julianDate
        val ut1Instant = convertUtcToUt1(utcJd, IersData.getUt1UtcDifference(J2000Constants.epoch))
        val eciPosition = EciPosition(3451956.8821, 3810279.8175, 3761878.593, ut1Instant)

        val ecefPosition = eciPosition.toEcefPosition()

        assertEquals(-3120195.2700, ecefPosition.x, 1e-6)
        assertEquals(4086570.5200, ecefPosition.y, 1e-6)
        assertEquals(3761687.3900, ecefPosition.z, 1e-6)
    }

    @Test
    fun ecefToEciConversionTest() {
        val utcJd = J2000Constants.epoch.julianDate
        val ut1Instant = convertUtcToUt1(utcJd, IersData.getUt1UtcDifference(J2000Constants.epoch))
        val ecefPosition = EcefPosition(-3120195.2700, 4086570.5200, 3761687.3900)

        val eciPosition = ecefPosition.toEciPosition(ut1Instant)

        assertEquals(3451956.8821, eciPosition.x, 1e-6)
        assertEquals(3810279.8175, eciPosition.y, 1e-6)
        assertEquals(3761878.593, eciPosition.z, 1e-6)
    }
}
