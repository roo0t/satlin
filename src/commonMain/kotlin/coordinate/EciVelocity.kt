package coordinate

import time.Ut1Instant

data class EciVelocity(
    override val x: MeterPerSec,
    override val y: MeterPerSec,
    override val z: MeterPerSec,
    override val referenceTime: Ut1Instant
) : EciCoordinate {
}
