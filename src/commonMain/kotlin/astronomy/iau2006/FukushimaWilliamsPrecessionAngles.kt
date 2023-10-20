package astronomy.iau2006

import longdouble.LongDouble
import longdouble.plus
import longdouble.times
import math.arcSecondToRadians

// https://www.iausofa.org/2023_1011_C.html

data class FukushimaWilliamsPrecessionAngles(
    val gammaBar: LongDouble,
    val phiBar: LongDouble,
    val psiBar: LongDouble,
    val epsilonA: LongDouble
)

// t = Julian century since J2000.0 in TT
fun calculateFukushimaWilliamsPrecessionAngles(t: LongDouble): FukushimaWilliamsPrecessionAngles {
    val gammaBar = arcSecondToRadians(
        (-0.052928 +
                (10.556378 +
                        (0.4932044 +
                                (-0.00031238 +
                                        (-0.000002788 +
                                                (0.0000000260) * t) * t) * t) * t) * t)
    )
    val phiBar = arcSecondToRadians(
        (84381.412819 +
                (-46.811016 +
                        (0.0511268 +
                                (0.00053289 +
                                        (-0.000000440 +
                                                (-0.0000000176) * t) * t) * t) * t) * t)
    )
    val psiBar = arcSecondToRadians(
        (-0.041775 +
                (5038.481484 +
                        (1.5584175 +
                                (-0.00018522 +
                                        (-0.000026452 +
                                                (-0.0000000148)
                                                * t) * t) * t) * t) * t)
    )
    val epsilonA = arcSecondToRadians(
        (84381.406 +
                (-46.836769 +
                        (-0.0001831 +
                                (0.00200340 +
                                        (-0.000000576 +
                                                (-0.0000000434) * t) * t) * t) * t) * t)
    )

    return FukushimaWilliamsPrecessionAngles(gammaBar, phiBar, psiBar, epsilonA)
}
