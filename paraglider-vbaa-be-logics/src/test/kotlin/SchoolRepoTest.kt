import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.ContextConfig
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.SchoolCrudFacade
import ru.kotlin.paraglider.vbaa.be.stubs.SchoolStub
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SchoolRepoTest {

    @Test
    fun createSuccessTest() {
        val repo = RepoSchoolInMemory()
        val crud = SchoolCrudFacade(contextConfig = ContextConfig(
            repoTest = repo
        ))
        val stub = SchoolStub.getModel().apply {
            headOfSchool = InstructorModel()
        }

        val context = SchoolContext(
            workMode = WorkMode.TEST,
            requestSchool = stub.copy().apply {
                id = SchoolIdModel.NONE
            },
            operation = CommonOperations.CREATE
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        with(context.responseSchool) {
            assertTrue { id.asString().isNotBlank() }
            assertEquals(stub.name, name)
            assertEquals(stub.welcomeVideoUrl, welcomeVideoUrl)
            assertEquals(stub.headOfSchool, headOfSchool)
            assertEquals(stub.shortInfo, shortInfo)
            assertEquals(stub.location, location)
            assertEquals(stub.instructorList, instructorList)
            assertEquals(stub.contactInfo, contactInfo)
            assertEquals(stub.serviceBasicInfo, serviceBasicInfo)
            assertEquals(stub.status, status)
        }
    }

//    @Test
//    fun readSuccessTest() {
//        val crud = SchoolCrudFacade()
//        val stubs = SchoolStub.getModels().map { it.copy() }.toMutableList()
//
//        val context = SchoolContext(
//            requestSchoolIds = stubs.map(SchoolModel::id).toList(),
//            operation = CommonOperations.READ
//        ).apply {
//            stubCase = CommonStubCase.SUCCESS
//        }
//        runBlocking {
//            crud.get(context)
//        }
//        assertEquals(CorStatus.SUCCESS, context.status)
//        stubs.forEachIndexed { i, expected ->
//            with(context.responseSchoolList[i]) {
//                assertEquals(expected.id, id)
//                assertEquals(expected.name, name)
//                assertEquals(expected.welcomeVideoUrl, welcomeVideoUrl)
//                assertEquals(expected.headOfSchool, headOfSchool)
//                assertEquals(expected.shortInfo, shortInfo)
//                assertEquals(expected.location, location)
//                assertEquals(expected.instructorList, instructorList)
//                assertEquals(expected.contactInfo, contactInfo)
//                assertEquals(expected.serviceBasicInfo, serviceBasicInfo)
//                assertEquals(expected.status, status)
//            }
//        }
//    }
//
//
//    @Test
//    fun updateSuccessTest() {
//        val crud = SchoolCrudFacade()
//        val context = SchoolContext(
//            requestSchool = SchoolStub.getModel().copy(),
//            operation = CommonOperations.UPDATE
//        ).apply {
//            stubCase = CommonStubCase.SUCCESS
//        }
//        runBlocking {
//            crud.update(context)
//        }
//        assertEquals(CorStatus.SUCCESS, context.status)
//        val expected = SchoolStub.getModel()
//        with(context.responseSchool) {
//            assertEquals(expected.id, id)
//            assertEquals(expected.name, name)
//            assertEquals(expected.welcomeVideoUrl, welcomeVideoUrl)
//            assertEquals(expected.headOfSchool, headOfSchool)
//            assertEquals(expected.shortInfo, shortInfo)
//            assertEquals(expected.location, location)
//            assertEquals(expected.instructorList, instructorList)
//            assertEquals(expected.contactInfo, contactInfo)
//            assertEquals(expected.serviceBasicInfo, serviceBasicInfo)
//            assertEquals(expected.status, status)
//        }
//    }
//
//
//    @Test
//    fun deleteSuccessTest() {
//        val crud = SchoolCrudFacade()
//        val deleteId = SchoolIdModel("1234")
//        val context = SchoolContext(
//            operation = CommonOperations.DELETE,
//            requestSchoolIds = listOf(deleteId)
//        ).apply {
//            stubCase = CommonStubCase.SUCCESS
//        }
//        runBlocking {
//            crud.delete(context)
//        }
//        assertEquals(CorStatus.SUCCESS, context.status)
//        val expected = SchoolStub.getModel()
//        with(context.responseSchool) {
//            assertEquals(deleteId, id)
//            assertEquals(expected.name, name)
//            assertEquals(expected.welcomeVideoUrl, welcomeVideoUrl)
//            assertEquals(expected.headOfSchool, headOfSchool)
//            assertEquals(expected.shortInfo, shortInfo)
//            assertEquals(expected.location, location)
//            assertEquals(expected.instructorList, instructorList)
//            assertEquals(expected.contactInfo, contactInfo)
//            assertEquals(expected.serviceBasicInfo, serviceBasicInfo)
//            assertEquals(expected.status, status)
//        }
//    }
//
//    @Test
//    fun searchSuccessTest() {
//        val crud = SchoolCrudFacade()
//        val context = SchoolContext(
//            requestPage = PaginatedModel(
//                lastId = SchoolIdModel("123"),
//                size = 10
//            ),
//            operation = CommonOperations.SEARCH
//        ).apply {
//            stubCase = CommonStubCase.SUCCESS
//        }
//        runBlocking {
//            crud.search(context)
//        }
//        assertEquals(CorStatus.SUCCESS, context.status)
//        val expectedList = SchoolStub.getModels()
//        expectedList.forEachIndexed { i, expected ->
//            with(context.responseSchoolList[i]) {
//                assertEquals(expected.id, id)
//                assertEquals(expected.name, name)
//                assertEquals(expected.welcomeVideoUrl, welcomeVideoUrl)
//                assertEquals(expected.headOfSchool, headOfSchool)
//                assertEquals(expected.shortInfo, shortInfo)
//                assertEquals(expected.location, location)
//                assertEquals(expected.instructorList, instructorList)
//                assertEquals(expected.contactInfo, contactInfo)
//                assertEquals(expected.serviceBasicInfo, serviceBasicInfo)
//                assertEquals(expected.status, status)
//            }
//        }
//    }
}