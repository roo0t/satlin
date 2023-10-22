package coordinate

import time.Ut1Instant

interface EciCoordinate : ThreeDimensionalCoordinate {
    val referenceTime: Ut1Instant
}
