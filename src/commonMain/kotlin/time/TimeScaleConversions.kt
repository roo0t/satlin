package time

import longdouble.LongDouble

// https://gssc.esa.int/navipedia/index.php/Transformations_between_Time_Systems

val taiTtDifference = LongDouble.valueOf(32.184 / 86400.0)
fun convertTtToTai(ttJd: LongDouble) = ttJd - taiTtDifference
fun convertTaiToTt(taiJd: LongDouble) = taiJd + taiTtDifference

val gpsTaiDifference = LongDouble.valueOf(19.0 / 86400.0)
fun convertTaiToGps(taiJd: LongDouble) = taiJd - gpsTaiDifference
fun convertGpsToTai(gpsJd: LongDouble) = gpsJd + gpsTaiDifference

fun convertTaiToUtc(taiJd: LongDouble, leapSeconds: Int) = taiJd - leapSeconds / 86400.0
fun convertUtcToTai(utcJd: LongDouble, leapSeconds: Int) = utcJd + leapSeconds / 86400.0
fun convertUtcToTai(utcJd: LongDouble) = convertUtcToTai(utcJd, LeapSeconds.at(Ut1Instant.ofJulianDate(utcJd))) // Ignore UTC-UT1 difference and approximate UT1 by UTC

fun convertUt1ToTt(instant: Ut1Instant, ut1UtcDifferenceSecond: Double): LongDouble {
    val utc = instant.julianDate - (ut1UtcDifferenceSecond / 86400.0)
    val tai = convertUtcToTai(utc, LeapSeconds.at(instant))
    return convertTaiToTt(tai)
}
