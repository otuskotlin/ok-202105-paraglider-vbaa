import kotlinx.coroutines.runBlocking
import ru.kotlin.paraglider.vbaa.app.ktor.stubs.SchoolStub
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.PaginatedModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolIdModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolStubCase
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.SchoolCrudFacade
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @crud - экземпляр класса-фасада бизнес-логики
 * @context - контекст, смапленный из транспортной модели запроса
 */
class SchoolCrudTest {

    @Test
    fun createSuccessTest() {
        val crud = SchoolCrudFacade()
        val context = SchoolContext(
            stubCase = SchoolStubCase.SUCCESS,
            requestSchool = SchoolStub.getModel{id = SchoolIdModel.NONE},
            operation = CommonOperations.CREATE
        )
        runBlocking {
            crud.create(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = SchoolStub.getModel()
        with(context.responseSchoolList[0]){
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
    fun readSuccessTest() {
        val crud = SchoolCrudFacade()
        val context = SchoolContext(
            stubCase = SchoolStubCase.SUCCESS,
            requestSchoolIds = SchoolStub.getModels().map(SchoolModel::id).toMutableSet(),
            operation = CommonOperations.READ
        )
        runBlocking {
            crud.get(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expectedList = SchoolStub.getModels()
        expectedList.forEachIndexed{ i, expected ->
            with(context.responseSchoolList[i]){
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
            stubCase = SchoolStubCase.SUCCESS,
            requestSchool = SchoolStub.getModel(),
            operation = CommonOperations.UPDATE
        )
        runBlocking {
            crud.update(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = SchoolStub.getModel()
        with(context.responseSchool){
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
        val context = SchoolContext(
            stubCase = SchoolStubCase.SUCCESS,
            requestSchool = SchoolStub.getModel(),
            operation = CommonOperations.DELETE
        )
        runBlocking {
            crud.delete(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expected = SchoolStub.getModel()
        with(context.responseSchool){
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
    fun searchSuccessTest() {
        val crud = SchoolCrudFacade()
        val context = SchoolContext(
            stubCase = SchoolStubCase.SUCCESS,
            requestPage = PaginatedModel(),
            operation = CommonOperations.SEARCH
        )
        runBlocking {
            crud.search(context)
        }
        assertEquals(CorStatus.SUCCESS, context.status)
        val expectedList = SchoolStub.getModels()
        expectedList.forEachIndexed{ i, expected ->
            with(context.responseSchoolList[i]){
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