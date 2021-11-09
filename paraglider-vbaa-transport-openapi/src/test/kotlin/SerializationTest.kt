import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import ru.kotlin.paraglider.vbaa.openapi.models.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializationTest {

    private val requestId = "321"
    private val createSchoolRequest = CreateSchoolRequest(
        messageType = "CreateSchoolRequest",
        requestId = requestId,
        createSchool = SchoolDTO(
            name = "MyNebo",
            shortInfo = "Flying school in Moscow area",
            welcomeVideoUrl = "some_url",
            headOfSchool = "123",
            contactInfo = ContactInfoDTO(
                socialMedia = listOf("instagram.com")
            ),
            location = LocationDTO(
                address = "Moscow area, Podolsk"
            ),
            services = setOf(),
            instructors = emptySet()
        ),
        debug = BaseDebugRequest(
            mode = BaseDebugRequest.Mode.STUB
        )
    )
    private val om = ObjectMapper()

    @Test
    fun serializationTest() {
        val json = om.writeValueAsString(createSchoolRequest)
        println(json)
        assertTrue("json must contain discriminator") {
            println("${createSchoolRequest::class.simpleName}")
            json.contains(""""messageType":"${createSchoolRequest::class.simpleName}"""")
        }
        assertTrue("json must contain school name") {
            json.contains(""""name":"${createSchoolRequest.createSchool?.name}"""")
        }
        assertTrue("json must contain school name") {
            json.contains(""""address":"${createSchoolRequest.createSchool?.location?.address}"""")
        }
    }

    @Test
    fun deserializeTest() {
        val json = om.writeValueAsString(createSchoolRequest)
        val deserialized = om.readValue(json, BaseMessage::class.java) as CreateSchoolRequest

        assertEquals(requestId, deserialized.requestId)
    }
}