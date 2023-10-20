package astronomy.iau2006

import longdouble.LongDouble
import longdouble.minus
import longdouble.plus
import longdouble.times
import math.arcSecondOfFullCircle
import math.microArcSecondToRadians
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// https://www.iausofa.org/2023_1011_C.html

data class NutationAngles(val dPsi: LongDouble, val dEpsilon: LongDouble)

fun calculateNutationAngles(t: LongDouble): NutationAngles {
    val fj2 = -2.7774e-6 * t

    val nutationAnglesIau2000 = calculateNutationAnglesIau2000(t)
    return NutationAngles(
        nutationAnglesIau2000.dPsi + nutationAnglesIau2000.dPsi * (0.4697e-6 + fj2),
        nutationAnglesIau2000.dEpsilon + nutationAnglesIau2000.dEpsilon * fj2
    )
}

fun calculateNutationAnglesIau2000(t: LongDouble): NutationAngles {
    val el = calculateMeanAnomalyOfMoon(t)
    val elp = calculateMeanAnomalyOfSun(t)
    val f = calculateMeanLongitudeOfMoonMinusThatOfAscendingNode(t)
    val d = calculateMeanElongationOfMoonFromSun(t)
    val om = calculateMeanLongitudeOfAscendingNodeOfMoon(t)

    var dp = LongDouble.ZERO
    var de = LongDouble.ZERO

    val twoPi = 2 * PI
    for (i in luniSolarNutationCoefficients.indices.reversed()) {
        val arg = (luniSolarNutationCoefficients[i].nl * el +
                luniSolarNutationCoefficients[i].nlp * elp +
                luniSolarNutationCoefficients[i].nf * f +
                luniSolarNutationCoefficients[i].nd * d +
                luniSolarNutationCoefficients[i].nom * om) % twoPi
        val sarg = sin(arg.toDouble())
        val carg = cos(arg.toDouble())

        dp += (luniSolarNutationCoefficients[i].sp + luniSolarNutationCoefficients[i].spt * t) * sarg +
                luniSolarNutationCoefficients[i].cp * carg
        de += (luniSolarNutationCoefficients[i].ce + luniSolarNutationCoefficients[i].cet * t) * carg +
                luniSolarNutationCoefficients[i].se * sarg
    }

    val dpsils = microArcSecondToRadians(dp)
    val depsls = microArcSecondToRadians(de)

    val al = (2.35555598 + 8328.6914269554 * t) % twoPi
    val af = (1.627905234 + 8433.466158131 * t) % twoPi
    val ad = (5.198466741 + 7771.3771468121 * t) % twoPi
    val aom = (2.18243920 - 33.757045 * t) % twoPi
    val apa = calculateGeneralAccumulatedPrecessionInLongitude(t)

    val alme = calculateMeanLongitudeOfMercury(t)
    val alve = calculateMeanLongitudeOfVenus(t)
    val alea = calculateMeanLongitudeOfEarth(t)
    val alma = calculateMeanLongitudeOfMars(t)
    val alju = calculateMeanLongitudeOfJupiter(t)
    val alsa = calculateMeanLongitudeOfSaturn(t)
    val alur = calculateMeanLongitudeOfUranus(t)

    val alne = (5.321159000 + 3.8127774000 * t) % (2 * PI)

    dp = LongDouble.ZERO
    de = LongDouble.ZERO

    for (i in planetaryNutationCoefficients.indices.reversed()) {
        val arg = (planetaryNutationCoefficients[i].nl * al +
                planetaryNutationCoefficients[i].nf * af +
                planetaryNutationCoefficients[i].nd * ad +
                planetaryNutationCoefficients[i].nom * aom +
                planetaryNutationCoefficients[i].nme * alme +
                planetaryNutationCoefficients[i].nve * alve +
                planetaryNutationCoefficients[i].nea * alea +
                planetaryNutationCoefficients[i].nma * alma +
                planetaryNutationCoefficients[i].nju * alju +
                planetaryNutationCoefficients[i].nsa * alsa +
                planetaryNutationCoefficients[i].nur * alur +
                planetaryNutationCoefficients[i].nne * alne +
                planetaryNutationCoefficients[i].npa * apa) % (2 * PI)
        val sarg = sin(arg.toDouble())
        val carg = cos(arg.toDouble())

        dp += planetaryNutationCoefficients[i].sp * sarg + planetaryNutationCoefficients[i].cp * carg
        de += planetaryNutationCoefficients[i].se * sarg + planetaryNutationCoefficients[i].ce * carg
    }

    val dpsipl = microArcSecondToRadians(dp)
    val depspl = microArcSecondToRadians(de)

    return NutationAngles(dpsils + dpsipl, depsls + depspl)
}
