package astronomy.iau2006

import astronomy.J2000Constants
import coordinate.Radian
import longdouble.LongDouble
import longdouble.plus
import longdouble.times
import math.*
import time.Ut1Instant
import kotlin.math.*

// https://www.iausofa.org/2023_1011_C.html

fun ttJdToJcSinceJ2000(ttJd: LongDouble) = (ttJd - 2451545) / 36525.0

// ported from iauC2t06a
fun calculateCelestialToTerrestrialConversionMatrix(
    ttJd: LongDouble, ut1Instant: Ut1Instant, xp: Radian, yp: Radian
): Matrix {
    var celestialToIntermediateMatrix = calculateCelestialToIntermediateConversionMatrix(ttJd)
    val earthRotationAngle = calculateEarthRotationAngle(ut1Instant)
    val sp = calculateTioLocator(ttJd)
    val polarMotionMatrix = makePolarMotionMatrix(xp, yp, sp)
    return makeCelestialToTerrestrialConversionMatrix(celestialToIntermediateMatrix, earthRotationAngle, polarMotionMatrix)
}

// ported from iauC2i06a
fun calculateCelestialToIntermediateConversionMatrix(ttJd: LongDouble): Matrix {
    val precessionNutationMatrix = calculatePrecessionNutationMatrix(ttJd)
    val cipCoordinates = getCipCoordinates(precessionNutationMatrix)
    val cioLocator = calculateCioLocator(ttJd, cipCoordinates)
    return makeCelestialToIntermediateMatrix(cipCoordinates, cioLocator)
}

// ported from iauPnm06a
fun calculatePrecessionNutationMatrix(ttJd: LongDouble): Matrix {
    val jc = ttJdToJcSinceJ2000(ttJd)

    val fwPrecessionAngles = calculateFukushimaWilliamsPrecessionAngles(jc)
    val nutationAngles = calculateNutationAngles(jc)

    return makePrecessionNutationMatrix(
        fwPrecessionAngles.gammaBar,
        fwPrecessionAngles.phiBar,
        fwPrecessionAngles.psiBar + nutationAngles.dPsi,
        fwPrecessionAngles.epsilonA + nutationAngles.dEpsilon
    )
}

// ported from iauFw2m
fun makePrecessionNutationMatrix(
    gammaBar: LongDouble,
    phiBar: LongDouble,
    psi: LongDouble,
    epsilon: LongDouble
): Matrix {
    var r = Matrix.identity(3)
    r = rotateMatrix(r, gammaBar.toDouble(), RotationAxis.Z_AXIS)
    r = rotateMatrix(r, phiBar.toDouble(), RotationAxis.X_AXIS)
    r = rotateMatrix(r, -psi.toDouble(), RotationAxis.Z_AXIS)
    r = rotateMatrix(r, -epsilon.toDouble(), RotationAxis.X_AXIS)
    return r
}

// ported from iauBpn2xy
fun getCipCoordinates(precessionNutationMatrix: Matrix) =
    Pair(precessionNutationMatrix[2, 0], precessionNutationMatrix[2, 1])

// ported from iauS06
fun calculateCioLocator(ttJd: LongDouble, cipCoordinates: Pair<Double, Double>): LongDouble {
    val sp = listOf(
        94.00e-6,
        3808.65e-6,
        -122.68e-6,
        -72574.11e-6,
        27.98e-6,
        15.62e-6
    )

    data class Term(val nfa: List<Int>, val s: Double, val c: Double);

    /* Terms of order t^0 */
    val s0 = listOf(
        /* 1-10 */
        Term(listOf(0, 0, 0, 0, 1, 0, 0, 0), -2640.73e-6, 0.39e-6),
        Term(listOf(0, 0, 0, 0, 2, 0, 0, 0), -63.53e-6, 0.02e-6),
        Term(listOf(0, 0, 2, -2, 3, 0, 0, 0), -11.75e-6, -0.01e-6),
        Term(listOf(0, 0, 2, -2, 1, 0, 0, 0), -11.21e-6, -0.01e-6),
        Term(listOf(0, 0, 2, -2, 2, 0, 0, 0), 4.57e-6, 0.00e-6),
        Term(listOf(0, 0, 2, 0, 3, 0, 0, 0), -2.02e-6, 0.00e-6),
        Term(listOf(0, 0, 2, 0, 1, 0, 0, 0), -1.98e-6, 0.00e-6),
        Term(listOf(0, 0, 0, 0, 3, 0, 0, 0), 1.72e-6, 0.00e-6),
        Term(listOf(0, 1, 0, 0, 1, 0, 0, 0), 1.41e-6, 0.01e-6),
        Term(listOf(0, 1, 0, 0, -1, 0, 0, 0), 1.26e-6, 0.01e-6),

        /* 11-20 */
        Term(listOf(1, 0, 0, 0, -1, 0, 0, 0), 0.63e-6, 0.00e-6),
        Term(listOf(1, 0, 0, 0, 1, 0, 0, 0), 0.63e-6, 0.00e-6),
        Term(listOf(0, 1, 2, -2, 3, 0, 0, 0), -0.46e-6, 0.00e-6),
        Term(listOf(0, 1, 2, -2, 1, 0, 0, 0), -0.45e-6, 0.00e-6),
        Term(listOf(0, 0, 4, -4, 4, 0, 0, 0), -0.36e-6, 0.00e-6),
        Term(listOf(0, 0, 1, -1, 1, -8, 12, 0), 0.24e-6, 0.12e-6),
        Term(listOf(0, 0, 2, 0, 0, 0, 0, 0), -0.32e-6, 0.00e-6),
        Term(listOf(0, 0, 2, 0, 2, 0, 0, 0), -0.28e-6, 0.00e-6),
        Term(listOf(1, 0, 2, 0, 3, 0, 0, 0), -0.27e-6, 0.00e-6),
        Term(listOf(1, 0, 2, 0, 1, 0, 0, 0), -0.26e-6, 0.00e-6),

        /* 21-30 */
        Term(listOf(0, 0, 2, -2, 0, 0, 0, 0), 0.21e-6, 0.00e-6),
        Term(listOf(0, 1, -2, 2, -3, 0, 0, 0), -0.19e-6, 0.00e-6),
        Term(listOf(0, 1, -2, 2, -1, 0, 0, 0), -0.18e-6, 0.00e-6),
        Term(listOf(0, 0, 0, 0, 0, 8, -13, -1), 0.10e-6, -0.05e-6),
        Term(listOf(0, 0, 0, 2, 0, 0, 0, 0), -0.15e-6, 0.00e-6),
        Term(listOf(2, 0, -2, 0, -1, 0, 0, 0), 0.14e-6, 0.00e-6),
        Term(listOf(0, 1, 2, -2, 2, 0, 0, 0), 0.14e-6, 0.00e-6),
        Term(listOf(1, 0, 0, -2, 1, 0, 0, 0), -0.14e-6, 0.00e-6),
        Term(listOf(1, 0, 0, -2, -1, 0, 0, 0), -0.14e-6, 0.00e-6),
        Term(listOf(0, 0, 4, -2, 4, 0, 0, 0), -0.13e-6, 0.00e-6),

        /* 31-33 */
        Term(listOf(0, 0, 2, -2, 4, 0, 0, 0), 0.11e-6, 0.00e-6),
        Term(listOf(1, 0, -2, 0, -3, 0, 0, 0), -0.11e-6, 0.00e-6),
        Term(listOf(1, 0, -2, 0, -1, 0, 0, 0), -0.11e-6, 0.00e-6)
    )

    /* Terms of order t^1 */
    val s1 = listOf(
        /* 1 - 3 */
        Term(listOf(0, 0, 0, 0, 2, 0, 0, 0), -0.07e-6, 3.57e-6),
        Term(listOf(0, 0, 0, 0, 1, 0, 0, 0), 1.73e-6, -0.03e-6),
        Term(listOf(0, 0, 2, -2, 3, 0, 0, 0), 0.00e-6, 0.48e-6)
    )

    /* Terms of order t^2 */
    val s2 = listOf(
        /* 1-10 */
        Term(listOf(0, 0, 0, 0, 1, 0, 0, 0), 743.52e-6, -0.17e-6),
        Term(listOf(0, 0, 2, -2, 2, 0, 0, 0), 56.91e-6, 0.06e-6),
        Term(listOf(0, 0, 2, 0, 2, 0, 0, 0), 9.84e-6, -0.01e-6),
        Term(listOf(0, 0, 0, 0, 2, 0, 0, 0), -8.85e-6, 0.01e-6),
        Term(listOf(0, 1, 0, 0, 0, 0, 0, 0), -6.38e-6, -0.05e-6),
        Term(listOf(1, 0, 0, 0, 0, 0, 0, 0), -3.07e-6, 0.00e-6),
        Term(listOf(0, 1, 2, -2, 2, 0, 0, 0), 2.23e-6, 0.00e-6),
        Term(listOf(0, 0, 2, 0, 1, 0, 0, 0), 1.67e-6, 0.00e-6),
        Term(listOf(1, 0, 2, 0, 2, 0, 0, 0), 1.30e-6, 0.00e-6),
        Term(listOf(0, 1, -2, 2, -2, 0, 0, 0), 0.93e-6, 0.00e-6),

        /* 11-20 */
        Term(listOf(1, 0, 0, -2, 0, 0, 0, 0), 0.68e-6, 0.00e-6),
        Term(listOf(0, 0, 2, -2, 1, 0, 0, 0), -0.55e-6, 0.00e-6),
        Term(listOf(1, 0, -2, 0, -2, 0, 0, 0), 0.53e-6, 0.00e-6),
        Term(listOf(0, 0, 0, 2, 0, 0, 0, 0), -0.27e-6, 0.00e-6),
        Term(listOf(1, 0, 0, 0, 1, 0, 0, 0), -0.27e-6, 0.00e-6),
        Term(listOf(1, 0, -2, -2, -2, 0, 0, 0), -0.26e-6, 0.00e-6),
        Term(listOf(1, 0, 0, 0, -1, 0, 0, 0), -0.25e-6, 0.00e-6),
        Term(listOf(1, 0, 2, 0, 1, 0, 0, 0), 0.22e-6, 0.00e-6),
        Term(listOf(2, 0, 0, -2, 0, 0, 0, 0), -0.21e-6, 0.00e-6),
        Term(listOf(2, 0, -2, 0, -1, 0, 0, 0), 0.20e-6, 0.00e-6),

        /* 21-25 */
        Term(listOf(0, 0, 2, 2, 2, 0, 0, 0), 0.17e-6, 0.00e-6),
        Term(listOf(2, 0, 2, 0, 2, 0, 0, 0), 0.13e-6, 0.00e-6),
        Term(listOf(2, 0, 0, 0, 0, 0, 0, 0), -0.13e-6, 0.00e-6),
        Term(listOf(1, 0, 2, -2, 2, 0, 0, 0), -0.12e-6, 0.00e-6),
        Term(listOf(0, 0, 2, 0, 0, 0, 0, 0), -0.11e-6, 0.00e-6)
    )

    /* Terms of order t^3 */
    val s3 = listOf(
        /* 1-4 */
        Term(listOf(0, 0, 0, 0, 1, 0, 0, 0), 0.30e-6, -23.42e-6),
        Term(listOf(0, 0, 2, -2, 2, 0, 0, 0), -0.03e-6, -1.46e-6),
        Term(listOf(0, 0, 2, 0, 2, 0, 0, 0), -0.01e-6, -0.25e-6),
        Term(listOf(0, 0, 0, 0, 2, 0, 0, 0), 0.00e-6, 0.23e-6)
    )

    /* Terms of order t^4 */
    val s4 = listOf(
        /* 1-1 */
        Term(listOf(0, 0, 0, 0, 1, 0, 0, 0), -0.26e-6, -0.01e-6)
    )

    val t = ttJdToJcSinceJ2000(ttJd)

    val fundamentalArguments = listOf(
        calculateMeanAnomalyOfMoon(t),
        calculateMeanAnomalyOfSun(t),
        calculateMeanLongitudeOfMoonMinusThatOfAscendingNode(t),
        calculateMeanElongationOfMoonFromSun(t),
        calculateMeanLongitudeOfAscendingNodeOfMoon(t),
        calculateMeanLongitudeOfVenus(t),
        calculateMeanLongitudeOfEarth(t),
        calculateGeneralAccumulatedPrecessionInLongitude(t),
    )

    val w0 = sp[0] + s0.sumOf {
        val a = it.nfa.mapIndexed { i, n -> n * fundamentalArguments[i] }.reduce(LongDouble::plus)
        it.s * sin(a.toDouble()) + it.c * cos(a.toDouble())
    }
    val w1 = sp[1] + s1.sumOf {
        val a = it.nfa.mapIndexed { i, n -> n * fundamentalArguments[i] }.reduce(LongDouble::plus)
        it.s * sin(a.toDouble()) + it.c * cos(a.toDouble())
    }
    val w2 = sp[2] + s2.sumOf {
        val a = it.nfa.mapIndexed { i, n -> n * fundamentalArguments[i] }.reduce(LongDouble::plus)
        it.s * sin(a.toDouble()) + it.c * cos(a.toDouble())
    }
    val w3 = sp[3] + s3.sumOf {
        val a = it.nfa.mapIndexed { i, n -> n * fundamentalArguments[i] }.reduce(LongDouble::plus)
        it.s * sin(a.toDouble()) + it.c * cos(a.toDouble())
    }
    val w4 = sp[4] + s4.sumOf {
        val a = it.nfa.mapIndexed { i, n -> n * fundamentalArguments[i] }.reduce(LongDouble::plus)
        it.s * sin(a.toDouble()) + it.c * cos(a.toDouble())
    }
    val w5 = sp[5]

    return arcSecondToRadians(w0 + (w1 + (w2 + (w3 + (w4 + w5 * t) * t) * t) * t) * t) -
            cipCoordinates.first * cipCoordinates.second / 2.0;
}

// ported from iauC2ixys
fun makeCelestialToIntermediateMatrix(cipCoordinates: Pair<Double, Double>, s: LongDouble): Matrix {
    val r2 = cipCoordinates.first.pow(2) + cipCoordinates.second.pow(2)
    val e = if (r2 > 0.0) atan2(cipCoordinates.second, cipCoordinates.first) else 0.0
    val d = atan(sqrt(r2 / (1.0 - r2)))

    var r = Matrix.identity(3)
    r = rotateMatrix(r, e, RotationAxis.Z_AXIS)
    r = rotateMatrix(r, d, RotationAxis.Y_AXIS)
    r = rotateMatrix(r, -(e + s).toDouble(), RotationAxis.Z_AXIS)
    return r
}

// ported from iauEra00
fun calculateEarthRotationAngle(instant: Ut1Instant): LongDouble {
    val t = (instant - J2000Constants.epoch).days
    val f = t % 1.0
    val theta = (2 * PI * (f + 0.7790572732640 + 0.00273781191135448 * t))
    val w = theta % (2 * PI)
    return if (w < 0.0) w + 2 * PI else w
}

// ported from iauSp00
fun calculateTioLocator(ttJd: LongDouble): LongDouble {
    val t = ttJdToJcSinceJ2000(ttJd)
    return arcSecondToRadians(-47e-6 * t)
}

fun makeCelestialToTerrestrialConversionMatrix(
    celestialToIntermediateMatrix: Matrix, earthRotationAngle: LongDouble, polarMotionMatrix: Matrix
) = polarMotionMatrix * rotateMatrix(celestialToIntermediateMatrix, earthRotationAngle.toDouble(), RotationAxis.Z_AXIS)
