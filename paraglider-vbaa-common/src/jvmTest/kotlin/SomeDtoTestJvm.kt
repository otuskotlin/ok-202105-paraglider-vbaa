import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.paraglider.common.kmp.SuspendRequest
import kotlin.test.assertEquals

class SomeDtoTestJvm {
    @Test
    fun suspendTest() = runBlocking {
        assertEquals("Suspend JVM", SuspendRequest().request())
    }

}