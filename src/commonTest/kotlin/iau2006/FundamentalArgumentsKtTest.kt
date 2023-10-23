package iau2006

import astronomy.iau2006.calculateMeanAnomalyOfMoon
import astronomy.iau2006.calculateMeanAnomalyOfSun
import longdouble.toLongDouble
import kotlin.test.Test
import kotlin.test.assertEquals

class FundamentalArgumentsKtTest {
    @Test
    fun testCalculateMeanAnomalyOfMoon() {
        assertEquals(
            5.132369751108684150,
            calculateMeanAnomalyOfMoon(0.80.toLongDouble()).toDouble(),
            1e-12)
    }

    @Test
    fun testCalculateMeanAnomalyOfSun() {
        assertEquals(
            6.226797973505507345,
            calculateMeanAnomalyOfSun(0.80.toLongDouble()).toDouble(),
            1e-12)
    }
}
