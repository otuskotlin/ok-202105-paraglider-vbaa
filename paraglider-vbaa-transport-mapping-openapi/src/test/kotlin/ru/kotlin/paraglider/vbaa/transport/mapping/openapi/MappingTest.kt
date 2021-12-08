package ru.kotlin.paraglider.vbaa.transport.mapping.openapi

import org.junit.Test
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.openapi.models.*
import java.time.LocalDate
import kotlin.test.assertEquals

class MappingTest {

    @Test
    fun setUpdateQueryMappingTest() {
        val query = UpdateSchoolRequest(
            requestId = "123",
            updateSchool = UpdateSchool(
                name = "My Sky",
                welcomeVideoUrl = "https://www.youtube.com/watch?v=HxbexLNDxZI",
                headOfSchool = "123",
                shortInfo = "paragliding school",
                location = LocationDTO(
                    address = "Moscow, Podolsk",
                    geolocation = "someGeo",
                    shortInfo = "someInfo"
                ),
                instructors = setOf(),
                contactInfo = ContactInfoDTO(
                    mobilePhones = listOf("89660000000"),
                    socialMedia = listOf("someVkUrl", "@my_nebo.ru"),
                    email = "someEmail"
                ),
                services = setOf(),
                status = SchoolStatusDTO.ACTIVE,
                schoolId = "1"
            )
        )
        val context = SchoolContext().apply { stubCase = CommonStubCase.SUCCESS }.setQuery(query)
        assertEquals("123", context.onRequest)
        assertEquals("https://www.youtube.com/watch?v=HxbexLNDxZI", context.requestSchool.welcomeVideoUrl)
        assertEquals("Moscow, Podolsk", context.requestSchool.location.address)
        assertEquals("@my_nebo.ru", context.requestSchool.contactInfo.socialMedia[1])
        assertEquals(SchoolStatusModel.ACTIVE, context.requestSchool.status)
    }

    @Test
    fun updateResponseMappingTest() {
        val context = SchoolContext(
            onRequest = "321",
            requestSchoolIds = listOf(SchoolIdModel("1")),
            responseSchool = SchoolModel(
                id = SchoolIdModel("1"),
                name = "My Sky",
                welcomeVideoUrl = "https://www.youtube.com/watch?v=HxbexLNDxZI",
                headOfSchool = UserIdModel("321"),
                shortInfo = "paragliding school",
                location = LocationModel(
                    address = "Moscow, Podolsk",
                    geolocation = "someGeo",
                    shortInfo = "someInfo"
                ),
                instructors = setOf(),
                contactInfo = ContactInfoModel(
                    mobilePhones = listOf("89660000000"),
                    socialMedia = listOf("someVkUrl", "@my_nebo.ru"),
                    email = "someEmail"
                ),
                services = setOf(),
                status = SchoolStatusModel.PENDING_ACTIVATION,
                permissions = mutableSetOf(PermissionModel.READ)
            ),
        ).apply {
            errors = mutableListOf(CommonErrorModel(level = IError.Level.WARNING, message = "some warning"))
        }

        val response = context.toUpdateResponse()
        assertEquals("1", response.updatedSchool?.schoolId)
        assertEquals("321", response.requestId)
        assertEquals("My Sky", response.updatedSchool?.name)
        assertEquals("https://www.youtube.com/watch?v=HxbexLNDxZI", response.updatedSchool?.welcomeVideoUrl)
        assertEquals("someGeo", response.updatedSchool?.location?.geolocation)
        assertEquals("someEmail", response.updatedSchool?.contactInfo?.email)
        assertEquals("some warning", response.errors?.get(0)?.message)
    }

}