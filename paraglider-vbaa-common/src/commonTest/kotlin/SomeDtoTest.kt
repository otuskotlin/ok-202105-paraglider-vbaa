import ru.paraglider.common.kmp.Request
import ru.paraglider.common.kmp.SomeDto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SomeDtoTest {
    @Test
    fun someDtoTest() {
        assertEquals("str", SomeDto(name = "str").name)
    }

    @Test
    fun requestTest() {
        assertTrue("Request.request must return \"some\"") {
            Request().request().contains("Some")
        }
    }
}