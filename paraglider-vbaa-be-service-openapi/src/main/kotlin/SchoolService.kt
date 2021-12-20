import exception.DataNotAllowedException
import org.slf4j.event.Level
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.SchoolCrudFacade
import ru.kotlin.paraglider.vbaa.be.mpLogger
import ru.kotlin.paraglider.vbaa.openapi.models.*
import ru.kotlin.paraglider.vbaa.transport.mapping.openapi.*

class SchoolService(private val schoolCrud: SchoolCrudFacade) {
    private val logger = mpLogger(this::class.java)

    suspend fun handleSchool(context: SchoolContext, request: BaseMessage): BaseMessage = try {
        when (request) {
            is InitSchoolRequest -> initSchool(context, request)
            is CreateSchoolRequest -> createSchool(context, request)
            is GetSchoolRequest -> getSchoolList(context, request)
            is UpdateSchoolRequest -> updateSchool(context, request)
            is DeleteSchoolRequest -> deleteSchool(context, request)
            is SearchSchoolRequest -> searchSchools(context, request)
            else -> throw DataNotAllowedException("Request is not Allowed", request)
        }
    } catch (e: Throwable) {
        errorSchool(context, e)
    }

    suspend fun createSchool(context: SchoolContext, request: CreateSchoolRequest): CreateSchoolResponse {
        context.setQuery(request)
        return context.handle("create-school", SchoolContext::toCreateResponse) {
            schoolCrud.create(it)
        }
    }

    suspend fun updateSchool(context: SchoolContext, request: UpdateSchoolRequest): UpdateSchoolResponse {
        context.setQuery(request)
        return context.handle("update-school", SchoolContext::toUpdateResponse) {
            schoolCrud.update(it)
        }
    }

    suspend fun getSchoolList(context: SchoolContext, request: GetSchoolRequest): GetSchoolResponse {
        context.setQuery(request)
        return context.handle("get-school-list", SchoolContext::toGetResponse) {
            schoolCrud.get(it)
        }
    }

    suspend fun searchSchools(context: SchoolContext, request: SearchSchoolRequest): SearchSchoolResponse {
        context.setQuery(request)
        return context.handle("search-school", SchoolContext::toSearchResponse) {
            schoolCrud.search(it)
        }
    }

    suspend fun deleteSchool(context: SchoolContext, request: DeleteSchoolRequest): DeleteSchoolResponse {
        context.setQuery(request)
        return context.handle("delete-school", SchoolContext::toDeleteResponse) {
            schoolCrud.delete(it)
        }
    }

    suspend fun initSchool(context: SchoolContext, request: InitSchoolRequest): InitSchoolResponse {
        context.setQuery(request)
        return context.handle("init-school", SchoolContext::toInitResponse)
    }

    suspend fun errorSchool(context: SchoolContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.toGetResponse()
    }

    private suspend fun <T> SchoolContext.handle(
        logId: String,
        mapper: SchoolContext.() -> T,
        block: suspend (SchoolContext) -> Unit = {}
    ): T {
        logger.log(
            msg = "Request got, query = {}",
            level = Level.INFO,
            data = toLog("$logId-request-got")
        )
        block(this)
        return mapper().also {
            logger.log(
                msg = "Response ready, response = {}",
                level = Level.INFO,
                data = toLog("$logId-request-handled")
            )
        }
    }
}