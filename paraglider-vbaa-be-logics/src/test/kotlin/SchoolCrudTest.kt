import kotlinx.coroutines.runBlocking
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.stubs.SchoolStub
import org.junit.Test
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.SchoolCrudFacade
import kotlin.test.assertEquals

/**
 * @crud - экземпляр класса-фасада бизнес-логики
 * @context - контекст, смапленный из транспортной модели запроса
 */
class SchoolCrudTest {

    @Test
    fun createSuccessTest() {
        val crud = SchoolCrudFacade()
        val stub = SchoolStub.getModel()

        val context = SchoolContext(
            requestSchool = stub.copy().apply {
                id = SchoolIdModel.NONE
            },
            operation = CommonOperations.CREATE
        ).apply {
            stubCase = CommonStubCase.SUCCESS
        }
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        with(context.responseSchool) {
            assertEquals(stub.id, id)
            assertEquals(stub.name, name)
            assertEquals(stub.welcomeVideoUrl, welcomeVideoUrl)
            assertEquals(stub.headOfSchool, headOfSchool)
            assertEquals(stub.shortInfo, shortInfo)
            assertEquals(stub.location, location)
            assertEquals(stub.instructorList, instructorList)
            assertEquals(stub.contactInfo, contactInfo)
            assertEquals(stub.serviceBasicInfo, serviceBasicInfo)
            assertEquals(stub.status, status)
            assertEquals(stub.permissions, permissions)
        }
    }

    @Test
    fun createFailedTest() {
        val crud = SchoolCrudFacade()
        val stub = SchoolStub.getModel()

        val context = SchoolContext(
            requestSchool = stub.copy().apply {
                id = SchoolIdModel.NONE
                contactInfo = ContactInfoModel(
                    email = "invalidEmail"
                )
                welcomeVideoUrl = "invalidUrl"
            },
            operation = CommonOperations.CREATE
        ).apply {
            stubCase = CommonStubCase.SUCCESS
        }
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.ERROR, context.status)
        assertEquals(2, context.errors.size)
    }

    @Test
    fun readSuccessTest() {
        val crud = SchoolCrudFacade()
        val stubs = SchoolStub.getModels().map { it.copy() }.toMutableList()

        val context = SchoolContext(
            requestSchoolIds = stubs.map(SchoolModel::id).toMutableSet(),
            operation = CommonOperations.READ
        ).apply {
            stubCase = CommonStubCase.SUCCESS
        }
        runBlocking {
            crud.get(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        stubs.forEachIndexed { i, expected ->
            with(context.responseSchoolList[i]) {
                assertEquals(expected.id, id)
                assertEquals(expected.name, name)
                assertEquals(expected.welcomeVideoUrl, welcomeVideoUrl)
                assertEquals(expected.headOfSchool, headOfSchool)
                assertEquals(expected.shortInfo, shortInfo)
                assertEquals(expected.location, location)
                assertEquals(expected.instructorList, instructorList)
                assertEquals(expected.contactInfo, contactInfo)
                assertEquals(expected.serviceBasicInfo, serviceBasicInfo)
                assertEquals(expected.status, status)
                assertEquals(expected.permissions, permissions)
            }
        }
    }


    @Test
    fun updateSuccessTest() {
        val crud = SchoolCrudFacade()
        val context = SchoolContext(
            requestSchool = SchoolStub.getModel().copy(),
            operation = CommonOperations.UPDATE
        ).apply {
            stubCase = CommonStubCase.SUCCESS
        }
        runBlocking {
            crud.update(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = SchoolStub.getModel()
        with(context.responseSchool) {
            assertEquals(expected.id, id)
            assertEquals(expected.name, name)
            assertEquals(expected.welcomeVideoUrl, welcomeVideoUrl)
            assertEquals(expected.headOfSchool, headOfSchool)
            assertEquals(expected.shortInfo, shortInfo)
            assertEquals(expected.location, location)
            assertEquals(expected.instructorList, instructorList)
            assertEquals(expected.contactInfo, contactInfo)
            assertEquals(expected.serviceBasicInfo, serviceBasicInfo)
            assertEquals(expected.status, status)
            assertEquals(expected.permissions, permissions)
        }
    }


    @Test
    fun deleteSuccessTest() {
        val crud = SchoolCrudFacade()
        val deleteId = SchoolIdModel("1234")
        val context = SchoolContext(
            operation = CommonOperations.DELETE,
            requestSchoolIds = setOf(deleteId)
        ).apply {
            stubCase = CommonStubCase.SUCCESS
        }
        runBlocking {
            crud.delete(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = SchoolStub.getModel()
        with(context.responseSchool) {
            assertEquals(deleteId, id)
            assertEquals(expected.name, name)
            assertEquals(expected.welcomeVideoUrl, welcomeVideoUrl)
            assertEquals(expected.headOfSchool, headOfSchool)
            assertEquals(expected.shortInfo, shortInfo)
            assertEquals(expected.location, location)
            assertEquals(expected.instructorList, instructorList)
            assertEquals(expected.contactInfo, contactInfo)
            assertEquals(expected.serviceBasicInfo, serviceBasicInfo)
            assertEquals(expected.status, status)
            assertEquals(expected.permissions, permissions)
        }
    }

    @Test
    fun searchSuccessTest() {
        val crud = SchoolCrudFacade()
        val context = SchoolContext(
            requestPage = PaginatedModel(
                lastId = SchoolIdModel("123"),
                size = 10
            ),
            operation = CommonOperations.SEARCH
        ).apply {
            stubCase = CommonStubCase.SUCCESS
        }
        runBlocking {
            crud.search(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expectedList = SchoolStub.getModels()
        expectedList.forEachIndexed { i, expected ->
            with(context.responseSchoolList[i]) {
                assertEquals(expected.id, id)
                assertEquals(expected.name, name)
                assertEquals(expected.welcomeVideoUrl, welcomeVideoUrl)
                assertEquals(expected.headOfSchool, headOfSchool)
                assertEquals(expected.shortInfo, shortInfo)
                assertEquals(expected.location, location)
                assertEquals(expected.instructorList, instructorList)
                assertEquals(expected.contactInfo, contactInfo)
                assertEquals(expected.serviceBasicInfo, serviceBasicInfo)
                assertEquals(expected.status, status)
                assertEquals(expected.permissions, permissions)
            }
        }
    }
}