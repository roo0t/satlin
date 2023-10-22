package astronomy.iers

import time.Ut1Instant

object IersData {
    private val iersDataRows = IersDataReader.readIersDataRows(FinalsAll.data)

    fun getUt1UtcDifference(instant: Ut1Instant): Double {
        val iersDataRowOfInstant = getIersDataRowOfInstant(instant)
        return iersDataRowOfInstant.ut1UtcDifference
    }

    fun getPolarMotionCoordinates(instant: Ut1Instant): Pair<Double, Double> {
        val iersDataRowOfInstant = getIersDataRowOfInstant(instant)
        return Pair(iersDataRowOfInstant.pmX, iersDataRowOfInstant.pmY)
    }

    private fun getIersDataRowOfInstant(instant: Ut1Instant): IersDataRow {
        val i = iersDataRows.binarySearchBy(instant) {
            Ut1Instant.ofModifiedJulianDate(it.fractionalMjdUtc)
        }
        return if (i >= 0) {
            iersDataRows[i]
        } else if (i == -1) {
            iersDataRows[0]
        } else {
            val a = iersDataRows[-i - 1]
            val b = iersDataRows[-i - 2]
            if (instant - Ut1Instant.ofModifiedJulianDate(a.fractionalMjdUtc) <
                Ut1Instant.ofModifiedJulianDate(b.fractionalMjdUtc) - instant) {
                a
            } else {
                b
            }
        }
    }
}

data class IersDataRow(
    val year: Int,
    val month: Int,
    val day: Int,
    val fractionalMjdUtc: Double,
    val isPolarMotionPredictedValue: Boolean,
    val pmX: Double,
    val pmXError: Double,
    val pmY: Double,
    val pmYError: Double,
    val isUt1UtcDifferencePredictedValue: Boolean,
    val ut1UtcDifference: Double,
    val ut1UtcDifferenceError: Double,
    val lod: Double?,
    val lodError: Double?,
    val isNutationPredictedValue: Boolean,
    val nutationDX: Double,
    val nutationDXError: Double,
    val nutationDY: Double,
    val nutationDYError: Double,
    val pmXBulletinB: Double,
    val pmYBulletinB: Double,
    val ut1UtcDifferenceBulletinB: Double,
    val nutationDXBulletinB: Double,
    val nutationDYBulletinB: Double,
)
