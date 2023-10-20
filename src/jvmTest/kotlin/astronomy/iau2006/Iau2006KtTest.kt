package astronomy.iau2006

import longdouble.LongDouble
import longdouble.toLongDouble
import math.Matrix
import org.junit.jupiter.api.Test
import time.Ut1Instant
import kotlin.test.assertEquals

class Iau2006KtTest {
    @Test
    fun testCalculateCelestialToTerrestrialConversionMatrix() {
        val ttJd = LongDouble(2400000, 53736.5)
        val ut1Instant = Ut1Instant.ofModifiedJulianDate(53736.0)
        val xp = 2.55060238e-7
        val yp = 1.860359247e-6

        val result = calculateCelestialToTerrestrialConversionMatrix(ttJd, ut1Instant, xp, yp)

        assertEquals(-0.1810332128305897282, result[0, 0], 1e-12)
        assertEquals(0.9834769806938592296, result[0, 1], 1e-12)
        assertEquals(0.6555550962998436505e-4, result[0, 2], 1e-12)

        assertEquals(-0.9834768134136214897, result[1, 0], 1e-12)
        assertEquals(-0.1810332203649130832, result[1, 1], 1e-12)
        assertEquals(0.5749800844905594110e-3, result[1, 2], 1e-12)

        assertEquals(0.5773474024748545878e-3, result[2, 0], 1e-12)
        assertEquals(0.3961816829632690581e-4, result[2, 1], 1e-12)
        assertEquals(0.9999998325501747785, result[2, 2], 1e-12)
    }

    @Test
    fun testCalculateCelestialToIntermediateConversionMatrix() {
        val ttJd = LongDouble(2400000, 53736.5)

        val result = calculateCelestialToIntermediateConversionMatrix(ttJd)

        assertEquals(0.9999998323037159379, result[0, 0], 1e-12)
        assertEquals(0.5581121329587613787e-9, result[0, 1], 1e-12)
        assertEquals(-0.5791308487740529749e-3, result[0, 2], 1e-12)

        assertEquals(-0.2384253169452306581e-7, result[1, 0], 1e-12)
        assertEquals(0.9999999991917467827, result[1, 1], 1e-12)
        assertEquals(-0.4020579392895682558e-4, result[1, 2], 1e-12)

        assertEquals(0.5791308482835292617e-3, result[2, 0], 1e-12)
        assertEquals(0.4020580099454020310e-4, result[2, 1], 1e-12)
        assertEquals(0.9999998314954628695, result[2, 2], 1e-12)
    }

    @Test
    fun testCalculatePrecessionNutationMatrix() {
        val ttJd = LongDouble(2400000, 50124.4999)

        val result = calculatePrecessionNutationMatrix(ttJd)

        assertEquals(0.9999995832794205484, result[0, 0], 1e-12)
        assertEquals(0.8372382772630962111e-3, result[0, 1], 1e-14)
        assertEquals(0.3639684771140623099e-3, result[0, 2], 1e-14)

        assertEquals(-0.8372533744743683605e-3, result[1, 0], 1e-14)
        assertEquals(0.9999996486492861646, result[1, 1], 1e-12)
        assertEquals(0.4132905944611019498e-4, result[1, 2], 1e-14)

        assertEquals(-0.3639337469629464969e-3, result[2, 0], 1e-14)
        assertEquals(-0.4163377605910663999e-4, result[2, 1], 1e-14)
        assertEquals(0.9999999329094260057, result[2, 2], 1e-12)
    }

    @Test
    fun testCalculateFukushimaWilliamsPrecessionAngles() {
        val ttJd = LongDouble(2400000, 50124.4999)

        val t = ttJdToJcSinceJ2000(ttJd)
        val result = calculateFukushimaWilliamsPrecessionAngles(t)

        assertEquals(-0.2243387670997995690e-5, result.gammaBar.toDouble(), 1e-16)
        assertEquals(0.4091014602391312808, result.phiBar.toDouble(), 1e-12)
        assertEquals(-0.9501954178013031895e-3, result.psiBar.toDouble(), 1e-14)
        assertEquals(0.4091014316587367491, result.epsilonA.toDouble(), 1e-12)
    }

    @Test
    fun testCalculateNutationAngles() {
        val ttJd = LongDouble(2400000, 53736.5)

        val t = ttJdToJcSinceJ2000(ttJd)
        val result = calculateNutationAngles(t)

        assertEquals(-0.9630912025820308797e-5, result.dPsi.toDouble(), 1e-13)
        assertEquals(0.4063238496887249798e-4, result.dEpsilon.toDouble(), 1e-13)
    }

    @Test
    fun testCalculateNutationAnglesIau2000() {
        val ttJd = LongDouble(2400000, 53736.5)

        val t = ttJdToJcSinceJ2000(ttJd)
        val result = calculateNutationAnglesIau2000(t)

        assertEquals(-0.9630909107115518431e-5, result.dPsi.toDouble(), 1e-13)
        assertEquals(0.4063239174001678710e-4, result.dEpsilon.toDouble(), 1e-13)
    }

    @Test
    fun testMakePrecessionNutationMatrix() {
        val gamb = -0.2243387670997992368e-5.toLongDouble()
        val phib = 0.4091014602391312982.toLongDouble()
        val psi = -0.9501954178013015092e-3.toLongDouble()
        val eps = 0.4091014316587367472.toLongDouble()

        val precessionNutationMatrix = makePrecessionNutationMatrix(gamb, phib, psi, eps)

        assertEquals(0.9999995505176007047, precessionNutationMatrix[0, 0], 1e-12)
        assertEquals(0.8695404617348192957e-3, precessionNutationMatrix[0, 1], 1e-12)
        assertEquals(0.3779735201865582571e-3, precessionNutationMatrix[0, 2], 1e-12)

        assertEquals(-0.8695404723772016038e-3, precessionNutationMatrix[1, 0], 1e-12)
        assertEquals(0.9999996219496027161, precessionNutationMatrix[1, 1], 1e-12)
        assertEquals(-0.1361752496887100026e-6, precessionNutationMatrix[1, 2], 1e-12)

        assertEquals(-0.3779734957034082790e-3, precessionNutationMatrix[2, 0], 1e-12)
        assertEquals(-0.1924880848087615651e-6, precessionNutationMatrix[2, 1], 1e-12)
        assertEquals(0.9999999285679971958, precessionNutationMatrix[2, 2], 1e-12)
    }

    @Test
    fun testGetCipCoordinates() {
        val rbpn = Matrix(
            listOf(
                listOf(
                    9.999962358680738e-1,
                    -2.516417057665452e-3,
                    -1.093569785342370e-3
                ),

                listOf(
                    2.516462370370876e-3,
                    9.999968329010883e-1,
                    4.006159587358310e-5
                ),

                listOf(
                    1.093465510215479e-3,
                    -4.281337229063151e-5,
                    9.999994012499173e-1
                )
            )
        )

        val result = getCipCoordinates(rbpn)

        assertEquals(1.093465510215479e-3, result.first, 1e-12)
        assertEquals(-4.281337229063151e-5, result.second, 1e-12)
    }

    @Test
    fun testCalculateCioLocator() {
        val ttJd = LongDouble(2400000, 53736.5)
        val x = 0.5791308486706011000e-3
        val y = 0.4020579816732961219e-4

        val result = calculateCioLocator(ttJd, Pair(x, y))

        assertEquals(-0.1220032213076463117e-7, result.toDouble(), 1e-18)
    }

    @Test
    fun testMakeCelestialToIntermediateMatrix() {
        val x = 0.5791308486706011000e-3
        val y = 0.4020579816732961219e-4
        val s = -0.1220040848472271978e-7

        val result = makeCelestialToIntermediateMatrix(Pair(x, y), s.toLongDouble())

        assertEquals(0.9999998323037157138, result[0, 0], 1e-12)
        assertEquals(0.5581984869168499149e-9, result[0, 1], 1e-12)
        assertEquals(-0.5791308491611282180e-3, result[0, 2], 1e-12)

        assertEquals(-0.2384261642670440317e-7, result[1, 0], 1e-12)
        assertEquals(0.9999999991917468964, result[1, 1], 1e-12)
        assertEquals(-0.4020579110169668931e-4, result[1, 2], 1e-12)

        assertEquals(0.5791308486706011000e-3, result[2, 0], 1e-12)
        assertEquals(0.4020579816732961219e-4, result[2, 1], 1e-12)
        assertEquals(0.9999998314954627590, result[2, 2], 1e-12)
    }

    @Test
    fun testCalculateEarthRotationAngle() {
        val instant = Ut1Instant.ofModifiedJulianDate(54388.0)

        val result = calculateEarthRotationAngle(instant)

        assertEquals(0.4022837240028158102, result.toDouble(), 1e-12)
    }

    @Test
    fun testCalculateTioLocator() {
        val ttJd = LongDouble(2400000, 52541.5)

        val result = calculateTioLocator(ttJd)

        assertEquals(-0.6216698469981019309e-11, result.toDouble(), 1e-12)
    }
}
