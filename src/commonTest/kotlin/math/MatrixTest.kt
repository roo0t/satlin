package math

import kotlin.test.Test
import kotlin.test.assertEquals

class MatrixTest {
    @Test
    fun multiplicationTest() {
        val a = Matrix(listOf(
            listOf(1.0, 2.0, 3.0),
            listOf(4.0, 5.0, 6.0),
        ))
        val b = Matrix(listOf(
            listOf(7.0, 8.0),
            listOf(9.0, 10.0),
            listOf(11.0, 12.0),
        ))

        val expectedResult = Matrix(listOf(
            listOf(58.0, 64.0),
            listOf(139.0, 154.0),
        ))

        assertEquals(0.0, (a * b - expectedResult).frobeniusNorm(), 1e-16)
    }
}
