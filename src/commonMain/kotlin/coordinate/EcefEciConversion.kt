package coordinate

import astronomy.iau2006.calculateCelestialToTerrestrialConversionMatrix
import astronomy.iers.IersData
import math.Matrix
import math.arcSecondToRadians
import time.Ut1Instant
import time.convertUt1ToTt

fun EciPosition.toEcefPosition(): EcefPosition {
    val c2tConversionMatrix = calculateC2tConversionMatrix(referenceTime)
    return EcefPosition(c2tConversionMatrix * toColumnMatrix())
}

fun EcefPosition.toEciPosition(referenceTime: Ut1Instant): EciPosition {
    val c2tConversionMatrix = calculateC2tConversionMatrix(referenceTime)
    return EciPosition(c2tConversionMatrix.solve(toColumnMatrix()), referenceTime)
}

private fun calculateC2tConversionMatrix(referenceTime: Ut1Instant): Matrix {
    val ttJd = convertUt1ToTt(referenceTime, IersData.getUt1UtcDifference(referenceTime))
    val polarMotionCoordinates = IersData.getPolarMotionCoordinates(referenceTime)
    return calculateCelestialToTerrestrialConversionMatrix(
        ttJd,
        referenceTime,
        arcSecondToRadians(polarMotionCoordinates.first),
        arcSecondToRadians(polarMotionCoordinates.second)
    )
}
