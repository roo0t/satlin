package time

import longdouble.LongDouble
import longdouble.toLongDouble

data class Ut1Duration(val seconds: LongDouble) : Comparable<Ut1Duration> {
    companion object {
        fun ofSeconds(seconds: LongDouble) = Ut1Duration(seconds)
        fun ofSeconds(seconds: Double) = Ut1Duration(seconds.toLongDouble())
        fun ofSeconds(seconds: Long) = Ut1Duration(seconds.toLongDouble())
        fun ofSeconds(seconds: Int) = Ut1Duration(seconds.toLongDouble())
        fun ofDays(days: LongDouble) = Ut1Duration(days * 86400)
        fun ofDays(days: Double) = ofDays(days.toLongDouble())
        fun ofDays(days: Long) = ofDays(days.toLongDouble())
        fun ofDays(days: Int) = ofDays(days.toLongDouble())
    }

    val days
        get() = seconds / 86400.0
    val julianCentury
        get() = days / 36525.0

    operator fun plus(rhs: Ut1Duration) = Ut1Duration(seconds + rhs.seconds)
    operator fun minus(rhs: Ut1Duration) = Ut1Duration(seconds - rhs.seconds)
    operator fun times(rhs: LongDouble) = Ut1Duration(seconds * rhs)
    operator fun times(rhs: Double) = Ut1Duration(seconds * rhs)
    operator fun div(rhs: LongDouble) = Ut1Duration(seconds / rhs)
    operator fun div(rhs: Double) = Ut1Duration(seconds / rhs)

    override operator fun compareTo(other: Ut1Duration) = seconds.compareTo(other.seconds)
}
