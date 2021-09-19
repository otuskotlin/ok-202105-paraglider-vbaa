package ru.kotlin.paraglider.vbaa.app.ktor.service

import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.SchoolCrudFacade
import ru.kotlin.paraglider.vbaa.openapi.models.*
import ru.kotlin.paraglider.vbaa.transport.mapping.openapi.*

class SchoolService(private val schoolCrud: SchoolCrudFacade) {

    suspend fun createSchool(context: SchoolContext, request: CreateSchoolRequest): CreateSchoolResponse {
        context.setQuery(request)
        schoolCrud.create(context)
        return context.toCreateResponse()
    }

    suspend fun updateSchool(context: SchoolContext, request: UpdateSchoolRequest): UpdateSchoolResponse {
        context.setQuery(request)
        schoolCrud.update(context)
        return context.toUpdateResponse()
    }

    suspend fun getSchoolList(context: SchoolContext, request: GetSchoolRequest): GetSchoolResponse {
        context.setQuery(request)
        schoolCrud.get(context)
        return context.toGetResponse()
    }

    suspend fun searchSchools(context: SchoolContext, request: SearchSchoolRequest): SearchSchoolResponse {
        context.setQuery(request)
        schoolCrud.search(context)
        return context.toSearchResponse()
    }

    suspend fun deleteSchool(context: SchoolContext, request: DeleteSchoolRequest): DeleteSchoolResponse {
        context.setQuery(request)
        schoolCrud.delete(context)
        return context.toDeleteResponse()
    }

    fun initSchool(context: SchoolContext, request: InitSchoolRequest): InitSchoolResponse {
        context.setQuery(request)
        return context.toInitResponse()
    }

    fun errorSchool(context: SchoolContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.toGetResponse()
    }
}