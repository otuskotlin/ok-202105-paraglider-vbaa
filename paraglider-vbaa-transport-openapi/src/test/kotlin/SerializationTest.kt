import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import ru.kotlin.paraglider.vbaa.openapi.models.*
import java.io.File
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
            headOfSchool = InstructorDTO(
                name = "Maria",
                surname = "Viklayeva",
                patronymic = "Igorevna",
                dateOfBirth = "1984-12-31",
                hoursOfFly = 1000,
                flyLocations = setOf("Chegem","Babadag","Krusevo",
                    "Marokko", "Юца", "Serbia", "Albania", "Alania"),
                shortInfo = "Paragliding is my life",
                certificateUrl = "some_cert_url",
                mobilePhone = "89661234567",
//                photo = File("/pic.jpg"),
                photo = "/pic.jpg",
                schoolIdList = setOf("1")
            ),
            contactInfo = ContactInfoDTO(
                socialMedia = listOf("instagram.com")
            ),
            location = LocationDTO(
                address = "Moscow area, Podolsk"
            ),
            serviceBasicInfo = listOf("Some service 1"),
            instructorList = emptyList()
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
            json.contains(""""mobilePhone":"${createSchoolRequest.createSchool?.headOfSchool?.mobilePhone}"""")
        }
    }

    @Test
    fun deserializeTest() {
        val json = om.writeValueAsString(createSchoolRequest)
        val deserialized = om.readValue(json, BaseMessage::class.java) as CreateSchoolRequest

        assertEquals("31.12.1984", deserialized.createSchool?.headOfSchool?.dateOfBirth)
        assertEquals(requestId, deserialized.requestId)
    }
}