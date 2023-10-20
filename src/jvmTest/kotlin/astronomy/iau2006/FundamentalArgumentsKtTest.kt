package astronomy.iau2006

import longdouble.LongDouble
import longdouble.toLongDouble
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

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
