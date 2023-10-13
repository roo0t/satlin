package longdouble

import kotlin.math.floor

class LongDouble private constructor(private val integerPart: Long, private val fractionPart: Double) {
    companion object {
        val ZERO = valueOf(0)

        fun valueOf(value: Float) = normalize(0, value.toDouble())
        fun valueOf(value: Double) = normalize(0, value)
        fun valueOf(value: Int) = normalize(value.toLong(), 0.0)
        fun valueOf(value: Long) = normalize(value, 0.0)

        private fun normalize(integer: Long, fraction: Double): LongDouble {
            val transfer = floor(fraction).toLong()
            return LongDouble(integer + transfer, fraction - transfer)
        }
    }

    fun toDouble() = integerPart.toDouble() + fractionPart

    operator fun plus(rhs: LongDouble) = normalize(integerPart + rhs.integerPart, fractionPart + rhs.fractionPart)
    operator fun plus(rhs: Double) = normalize(integerPart, fractionPart + rhs)
    operator fun plus(rhs: Long) = normalize(integerPart + rhs, fractionPart)
    operator fun plus(rhs: Int) = normalize(integerPart + rhs, fractionPart)

    operator fun minus(rhs: LongDouble) =
        normalize(integerPart - rhs.integerPart, fractionPart - rhs.fractionPart)
    operator fun minus(rhs: Double) = normalize(integerPart, fractionPart - rhs)
    operator fun minus(rhs: Long) = normalize(integerPart - rhs, fractionPart)
    operator fun minus(rhs: Int) = normalize(integerPart - rhs, fractionPart)

    operator fun times(rhs: LongDouble) =
        normalize(
            integerPart * rhs.integerPart,
            integerPart * rhs.fractionPart + fractionPart * rhs.integerPart + fractionPart * rhs.fractionPart)
    operator fun times(rhs: Double) = this * valueOf(rhs)
    operator fun times(rhs: Long) = normalize(integerPart * rhs, fractionPart * rhs)
    operator fun times(rhs: Int) = normalize(integerPart * rhs, fractionPart * rhs)

    operator fun div(rhs: LongDouble) = normalize(0, integerPart / rhs.toDouble()) +
                normalize(0, fractionPart / rhs.toDouble())
    operator fun div(rhs: Double) = normalize(0, integerPart / rhs) + normalize(0, fractionPart / rhs)
    operator fun div(rhs: Long) = this / rhs.toDouble()
    operator fun div(rhs: Int) = this / rhs.toDouble()

    operator fun compareTo(rhs: LongDouble): Int {
        if (integerPart == rhs.integerPart) {
            return fractionPart.compareTo(rhs.fractionPart)
        } else {
            return integerPart.compareTo(rhs.integerPart)
        }
    }
}

fun Double.toLongDouble() = LongDouble.valueOf(this)
fun Long.toLongDouble() = LongDouble.valueOf(this)
fun Int.toLongDouble() = LongDouble.valueOf(this)
