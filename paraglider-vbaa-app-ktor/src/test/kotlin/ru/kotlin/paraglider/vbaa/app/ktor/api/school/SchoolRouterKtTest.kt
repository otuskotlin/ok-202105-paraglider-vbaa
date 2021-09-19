package ru.kotlin.paraglider.vbaa.app.ktor.api.school

import org.junit.Test
import ru.kotlin.paraglider.vbaa.be.stubs.SchoolStub
import ru.kotlin.paraglider.vbaa.be.stubs.SchoolStub.responseSchoolStubOne
import ru.kotlin.paraglider.vbaa.openapi.models.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SchoolRouterKtTest : RouterTest() {
    val stubDebug = BaseDebugRequest(mode = BaseDebugRequest.Mode.STUB, stubCase = BaseDebugRequest.StubCase.SUCCESS)

    @Test
    fun testSchoolCreate() {
        val data = CreateSchoolRequest(debug = stubDebug, createSchool = SchoolStub.getCreateSchoolRequest().createSchool)
        testPostRequest<CreateSchoolResponse>(data, "/api/v1/school/create") {
            assertEquals(CreateSchoolResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(responseSchoolStubOne, createdSchool)
        }
    }

    @Test
    fun testSchoolList() {
        val data = GetSchoolRequest(schoolIdList = setOf("123"), debug = stubDebug)

        testPostRequest<GetSchoolResponse>(data, "/api/v1/school/list") {
            assertEquals(GetSchoolResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(responseSchoolStubOne, readSchoolList?.get(0))
        }
    }
    @Test
    fun testSchoolDelete() {
        val data = DeleteSchoolRequest(schoolId = "444", debug = stubDebug)

        testPostRequest<DeleteSchoolResponse>(data, "/api/v1/school/delete") {
            assertEquals(DeleteSchoolResponse.Result.SUCCESS, result)
            assertNull(errors)
        }
    }

}