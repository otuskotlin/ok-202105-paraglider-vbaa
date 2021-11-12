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
            headOfSchool = InstructorIdModel(id = "uuid-456")
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
            assertEquals(stub.instructors, instructors)
            assertEquals(stub.contactInfo, contactInfo)
            assertEquals(stub.services, services)
            assertEquals(stub.status, status)
        }
    }

    @Test
    fun readSuccessTest() {
        val repo = RepoSchoolInMemory(
            initObjects = SchoolStub.getModels().map { it.copy() }.toMutableList()
        )
        val crud = SchoolCrudFacade(contextConfig = ContextConfig(
            repoTest = repo
        ))
        val stubs = SchoolStub.getModels().map { it.copy() }.toMutableList()

        val context = SchoolContext(
            workMode = WorkMode.TEST,
            requestSchoolIds = stubs.map(SchoolModel::id).toList(),
            operation = CommonOperations.READ
        )
        runBlocking {
            crud.get(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        context.responseSchoolList = context.responseSchoolList.sortedBy { it.id.asString() }
        stubs.sortBy { it.id.asString() }
        stubs.forEachIndexed { i, expected ->
            with(context.responseSchoolList[i]) {
                assertEquals(expected.id, id)
                assertEquals(expected.name, name)
                assertEquals(expected.welcomeVideoUrl, welcomeVideoUrl)
                assertEquals(expected.headOfSchool, headOfSchool)
                assertEquals(expected.shortInfo, shortInfo)
                assertEquals(expected.location, location)
                assertEquals(expected.instructors, instructors)
                assertEquals(expected.contactInfo, contactInfo)
                assertEquals(expected.services, services)
                assertEquals(expected.status, status)
            }
        }
    }


    @Test
    fun updateSuccessTest() {
        val repo = RepoSchoolInMemory(
            initObjects = listOf(SchoolStub.getModel())
        )
        val crud = SchoolCrudFacade(contextConfig = ContextConfig(
            repoTest = repo
        ))
        val context = SchoolContext(
            workMode = WorkMode.TEST,
            requestSchool = SchoolStub.getModel().copy(),
            operation = CommonOperations.UPDATE
        )
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
            assertEquals(expected.instructors, instructors)
            assertEquals(expected.contactInfo, contactInfo)
            assertEquals(expected.services, services)
            assertEquals(expected.status, status)
        }
    }


    @Test
    fun deleteSuccessTest() {
        val repo = RepoSchoolInMemory()
        val crud = SchoolCrudFacade(contextConfig = ContextConfig(
            repoTest = repo
        ))

        val deleteId = SchoolIdModel("1234")
        val context = SchoolContext(
            workMode = WorkMode.TEST,
            operation = CommonOperations.DELETE,
            requestSchoolIds = listOf(deleteId)
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
            assertEquals(expected.instructors, instructors)
            assertEquals(expected.contactInfo, contactInfo)
            assertEquals(expected.services, services)
            assertEquals(expected.status, status)
        }
    }


    @Test
    fun searchSuccessTest() {
        val repo = RepoSchoolInMemory()
        val crud = SchoolCrudFacade(contextConfig = ContextConfig(
            repoTest = repo
        ))
        val context = SchoolContext(
            workMode = WorkMode.TEST,
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
                assertEquals(expected.instructors, instructors)
                assertEquals(expected.contactInfo, contactInfo)
                assertEquals(expected.instructors, instructors)
                assertEquals(expected.status, status)
            }
        }
    }
}