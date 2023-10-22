package math

import longdouble.LongDouble

val arcSecondOfFullCircle = LongDouble.valueOf(1296000)

fun arcSecondToRadians(arcSecond: Double): Double = arcSecond * 4.848136811095359935899141e-6

fun arcSecondToRadians(arcSecond: LongDouble): LongDouble {
    return arcSecond * LongDouble.valueOf(4.84813681109e-6) +
            arcSecond * LongDouble.valueOf(5.359935899141e-18)
}

fun microArcSecondToRadians(microArcSecond: LongDouble): LongDouble {
    return microArcSecond * LongDouble.valueOf(1e-7) *
            (LongDouble.valueOf(4.84813681109e-6) + LongDouble.valueOf(5.359935899141e-18))
}
