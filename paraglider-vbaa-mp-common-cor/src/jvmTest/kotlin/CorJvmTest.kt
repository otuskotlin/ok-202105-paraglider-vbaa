import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals
import kotlin.test.Test

class CorJvmTest {
    @Test
    fun corTest() {
        val chain = CorTest.chain
        val ctx = TestContext(some = 13)

        runBlocking { chain.exec(ctx) }

        assertEquals(17, ctx.some)
    }
}