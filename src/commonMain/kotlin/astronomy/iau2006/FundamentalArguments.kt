package astronomy.iau2006

import longdouble.LongDouble
import longdouble.plus
import longdouble.times
import math.arcSecondOfFullCircle
import math.arcSecondToRadians
import kotlin.math.PI

// ported from iauFal03
fun calculateMeanAnomalyOfMoon(t: LongDouble): LongDouble {
    val a = 485868.249036 +
            t * (1717915923.2178 +
            t * (31.8792 +
            t * (0.051635 +
            t * (-0.00024470))))
    return arcSecondToRadians(a % arcSecondOfFullCircle)
}

// ported from iauFalp03
fun calculateMeanAnomalyOfSun(t: LongDouble): LongDouble {
    val a = 1287104.79305 +
            t * (129596581.0481 +
            t * (-0.5532 +
            t * (0.000136 +
            t * (-0.00001149))))
    return arcSecondToRadians(a % arcSecondOfFullCircle)
}

fun calculateMeanLongitudeOfMoonMinusThatOfAscendingNode(t: LongDouble): LongDouble {
    val a = 335779.526232 +
            t * (1739527262.8478 +
            t * (-12.7512 +
            t * (-0.001037 +
            t * (0.00000417))))
    return arcSecondToRadians(a % arcSecondOfFullCircle)
}

fun calculateMeanElongationOfMoonFromSun(t: LongDouble): LongDouble {
    val d = 1072260.70369 +
            t * (1602961601.2090 +
            t * (-6.3706 +
            t * (0.006593 +
            t * (-0.00003169))))
    return arcSecondToRadians(d % arcSecondOfFullCircle)
}

fun calculateMeanLongitudeOfAscendingNodeOfMoon(t: LongDouble): LongDouble {
    val a = 450160.398036 +
            t * (-6962890.5431 +
            t * (7.4722 +
            t * (0.007702 +
            t * (-0.00005939))))
    return arcSecondToRadians(a % arcSecondOfFullCircle)
}

fun calculateGeneralAccumulatedPrecessionInLongitude(t: LongDouble) = (0.024381750 + 0.00000538691 * t) * t

fun calculateMeanLongitudeOfMercury(t: LongDouble) = (4.402608842 + 2608.7903141574 * t) % (2 * PI)

fun calculateMeanLongitudeOfVenus(t: LongDouble) = (3.176146697 + 1021.3285546211 * t) % (2 * PI)

fun calculateMeanLongitudeOfEarth(t: LongDouble) = (1.753470314 + 628.3075849991 * t) % (2 * PI)

fun calculateMeanLongitudeOfMars(t: LongDouble) = (6.203480913 + 334.0612426700 * t) % (2 * PI)

fun calculateMeanLongitudeOfJupiter(t: LongDouble) = (0.599546497 + 52.9690962641 * t) % (2 * PI)

fun calculateMeanLongitudeOfSaturn(t: LongDouble) = (0.874016757 + 21.3299104960 * t) % (2 * PI)

fun calculateMeanLongitudeOfUranus(t: LongDouble) = (5.481293872 + 7.4781598567 * t) % (2 * PI)
