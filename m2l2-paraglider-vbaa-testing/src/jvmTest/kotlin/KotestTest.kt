import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import kotlin.math.max

class KotestTest: StringSpec({

    "our first Test JVM" {
        1.toString() shouldBe "1"
    }

    "maximum of two numbers" {
        forAll(
            row(1, 5, 5),
            row(1, 0, 1),
            row(0, 0, 0)
        ) { a, b, max ->
            max(a, b) shouldBe max
        }
    }
})