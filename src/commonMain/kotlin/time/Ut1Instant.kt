package time

import longdouble.LongDouble
import longdouble.toLongDouble

data class Ut1Instant(val julianDate: LongDouble) {
    constructor(julianDate: Double) : this(julianDate.toLongDouble())
    constructor(julianDate: Long) : this(julianDate.toLongDouble())

    companion object {
        val JULIAN_DATE_EPOCH = ofJulianDate(0.0)
        val UNIX_TIME_EPOCH = ofJulianDate(2440587.5)
        val MODIFIED_JULIAN_DATE_EPOCH = ofJulianDate(2400000.5)
        val NTP_EPOCH = ofJulianDate(2415020.5)

        fun ofJulianDate(julianDate: LongDouble) = Ut1Instant(julianDate)
        fun ofJulianDate(julianDate: Double) = Ut1Instant(julianDate)
        fun ofJulianDate(julianDate: Long) = Ut1Instant(julianDate)

        fun ofModifiedJulianDate(modifiedJulianDate: LongDouble) =
            MODIFIED_JULIAN_DATE_EPOCH + Ut1Duration.ofDays(modifiedJulianDate)
        fun ofModifiedJulianDate(modifiedJulianDate: Double) =
            MODIFIED_JULIAN_DATE_EPOCH + Ut1Duration.ofDays(modifiedJulianDate)
        fun ofModifiedJulianDate(modifiedJulianDate: Long) =
            MODIFIED_JULIAN_DATE_EPOCH + Ut1Duration.ofDays(modifiedJulianDate)

        fun ofNtpTimestamp(ntpTimestamp: LongDouble) = NTP_EPOCH + Ut1Duration.ofSeconds(ntpTimestamp)
        fun ofNtpTimestamp(ntpTimestamp: Double) = NTP_EPOCH + Ut1Duration.ofSeconds(ntpTimestamp)
        fun ofNtpTimestamp(ntpTimestamp: Long) = NTP_EPOCH + Ut1Duration.ofSeconds(ntpTimestamp)

        fun ofUnixTime(unixTime: LongDouble) = UNIX_TIME_EPOCH + Ut1Duration.ofSeconds(unixTime)
        fun ofUnixTime(unixTime: Double) = UNIX_TIME_EPOCH + Ut1Duration.ofSeconds(unixTime)
        fun ofUnixTime(unixTime: Long) = UNIX_TIME_EPOCH + Ut1Duration.ofSeconds(unixTime)
    }

    val unixTime: LongDouble
        get() = (this - UNIX_TIME_EPOCH).seconds
    val ntpTimestamp: LongDouble
        get() = (this - NTP_EPOCH).seconds
    val modifiedJulianDate: LongDouble
        get() = (this - MODIFIED_JULIAN_DATE_EPOCH).days

    operator fun plus(duration: Ut1Duration) = Ut1Instant(julianDate + duration.days)
    operator fun minus(rhs: Ut1Instant) = Ut1Duration.ofDays(julianDate - rhs.julianDate)
    operator fun compareTo(rhs: Ut1Instant): Int = julianDate.compareTo(rhs.julianDate)

    fun toTerrestrialTime(differenceInSecond: LongDouble): LongDouble {
        return julianDate + differenceInSecond / 86400.0
    }
}
