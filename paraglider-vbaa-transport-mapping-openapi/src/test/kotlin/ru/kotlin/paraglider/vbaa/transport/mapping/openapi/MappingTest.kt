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
                headOfSchool = InstructorDTO(
                    schoolIdList = setOf("1"),
                    name = "Maria",
                    surname = "Viklayeva",
                    dateOfBirth = "1984-12-31"
                ),
                shortInfo = "paragliding school",
                location = LocationDTO(
                    address = "Moscow, Podolsk",
                    geolocation = "someGeo",
                    shortInfo = "someInfo"
                ),
                instructorList = listOf(
                    InstructorDTO(
                        schoolIdList = setOf("1"),
                        name = "Ivan",
                        surname = "Ivanov",
                        dateOfBirth = "1990-01-20"
                    )
                ),
                contactInfo = ContactInfoDTO(
                    mobilePhones = listOf("89660000000"),
                    socialMedia = listOf("someVkUrl", "@my_nebo.ru"),
                    email = "someEmail"
                ),
                serviceBasicInfo = listOf("adjust harness", "straps stretch out"),
                status = SchoolStatusDTO.ACTIVE,
                schoolId = "1"
            )
        )
        val context = SchoolContext().setQuery(query)
        assertEquals("123", context.onRequest)
        assertEquals("https://www.youtube.com/watch?v=HxbexLNDxZI", context.requestSchool.welcomeVideoUrl)
        assertEquals("Maria", context.requestSchool.headOfSchool.name)
        assertEquals(LocalDate.parse("1984-12-31"), context.requestSchool.headOfSchool.dateOfBirth)
        assertEquals("Ivanov", context.requestSchool.instructorList[0].surname)
        assertEquals("Moscow, Podolsk", context.requestSchool.location.address)
        assertEquals("@my_nebo.ru", context.requestSchool.contactInfo.socialMedia[1])
        assertEquals("straps stretch out", context.requestSchool.serviceBasicInfo[1])
        assertEquals(SchoolStatusModel.ACTIVE, context.requestSchool.status)
    }

    @Test
    fun updateResponseMappingTest() {
        val context = SchoolContext(
            onRequest = "321",
            requestSchoolIds = setOf(SchoolIdModel("1")),
            responseSchool = SchoolModel(
                id = SchoolIdModel("1"),
                name = "My Sky",
                welcomeVideoUrl = "https://www.youtube.com/watch?v=HxbexLNDxZI",
                headOfSchool = InstructorModel(
                    schoolIdList = setOf(SchoolIdModel("1")),
                    name = "Maria",
                    surname = "Viklayeva",
                    dateOfBirth = LocalDate.of(1984,12,31)
                ),
                shortInfo = "paragliding school",
                location = LocationModel(
                    address = "Moscow, Podolsk",
                    geolocation = "someGeo",
                    shortInfo = "someInfo"
                ),
                instructorList = listOf(
                    InstructorModel(
                        schoolIdList = setOf(SchoolIdModel("1")),
                        name = "Ivan",
                        surname = "Ivanov",
                        dateOfBirth = LocalDate.of(1990,1,20)
                    )
                ),
                contactInfo = ContactInfoModel(
                    mobilePhones = listOf("89660000000"),
                    socialMedia = listOf("someVkUrl", "@my_nebo.ru"),
                    email = "someEmail"
                ),
                serviceBasicInfo = listOf("adjust harness", "straps stretch out"),
                status = SchoolStatusModel.PENDING_ACTIVATION,
                permissions = mutableSetOf(PermissionModel.READ)
            ),
            errors = mutableListOf(CommonErrorModel(level = IError.Level.WARNING, message = "some warning")),
        )

        val response = context.toUpdateResponse()
        assertEquals("1", response.updatedSchool?.schoolId)
        assertEquals("321", response.requestId)
        assertEquals("My Sky", response.updatedSchool?.name)
        assertEquals("https://www.youtube.com/watch?v=HxbexLNDxZI", response.updatedSchool?.welcomeVideoUrl)
        assertEquals("1984-12-31", response.updatedSchool?.headOfSchool?.dateOfBirth)
        assertEquals("someGeo", response.updatedSchool?.location?.geolocation)
        assertEquals("Ivan", response.updatedSchool?.instructorList?.get(0)?.name)
        assertEquals("someEmail", response.updatedSchool?.contactInfo?.email)
        assertEquals("adjust harness", response.updatedSchool?.serviceBasicInfo?.get(0))
        assertEquals("some warning", response.errors?.get(0)?.message)
    }

}