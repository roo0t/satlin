package coordinate

typealias Radian = Double
typealias Degree = Double
typealias Meter = Double

fun Radian.toDegree(): Degree {
    return this / kotlin.math.PI * 180;
}

fun Degree.toRadian(): Radian {
    return this / 180 * kotlin.math.PI;
}
