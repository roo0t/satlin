package math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RotationMatrixTest {
    @Test
    fun testRotationAboutXAxis() {
        val phi = 0.3456789

        val r = Matrix(
            listOf(
                listOf(2.0, 3.0, 2.0),
                listOf(3.0, 2.0, 3.0),
                listOf(3.0, 4.0, 5.0),
            )
        )

        val result = rotateMatrix(r, phi, RotationAxis.X_AXIS)

        assertEquals(2.0, result[0, 0], 0.0)
        assertEquals(3.0, result[0, 1], 0.0)
        assertEquals(2.0, result[0, 2], 0.0)

        assertEquals(3.839043388235612460, result[1, 0], 1e-12)
        assertEquals(3.237033249594111899, result[1, 1], 1e-12)
        assertEquals(4.516714379005982719, result[1, 2], 1e-12)

        assertEquals(1.806030415924501684, result[2, 0], 1e-12)
        assertEquals(3.085711545336372503, result[2, 1], 1e-12)
        assertEquals(3.687721683977873065, result[2, 2], 1e-12)
    }

    @Test
    fun testRotationAboutZAxis() {
        val psi = 0.3456789
        val r = Matrix(
            listOf(
                listOf(2.0, 3.0, 2.0),
                listOf(3.0, 2.0, 3.0),
                listOf(3.0, 4.0, 5.0),
            )
        )

        val result = rotateMatrix(r, psi, RotationAxis.Z_AXIS)

        assertEquals(2.898197754208926769, result[0, 0], 1e-12)
        assertEquals(3.500207892850427330, result[0, 1], 1e-12)
        assertEquals(2.898197754208926769, result[0, 2], 1e-12)

        assertEquals(2.144865911309686813, result[1, 0], 1e-12)
        assertEquals(0.865184781897815993, result[1, 1], 1e-12)
        assertEquals(2.144865911309686813, result[1, 2], 1e-12)

        assertEquals(3.0, result[2, 0], 1e-12)
        assertEquals(4.0, result[2, 1], 1e-12)
        assertEquals(5.0, result[2, 2], 1e-12)
    }
}
