package ru.kotlin.paraglider.vbaa.app.ktor.service

import ru.kotlin.paraglider.vbaa.app.ktor.stubs.SchoolStub
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.openapi.models.*
import ru.kotlin.paraglider.vbaa.transport.mapping.openapi.*

class SchoolService {
    fun createSchool(context: SchoolContext, request: CreateSchoolRequest): CreateSchoolResponse {
        context.setQuery(request)
        context.responseSchool = SchoolStub.getModel()
        return context.toCreateResponse()
    }

    fun updateSchool(context: SchoolContext, request: UpdateSchoolRequest): UpdateSchoolResponse {
        context.setQuery(request)
        context.responseSchool = SchoolStub.getModel()
        return context.toUpdateResponse()
    }

    fun getSchoolList(context: SchoolContext, request: GetSchoolRequest): GetSchoolResponse {
        context.setQuery(request)
        context.responseSchoolList = SchoolStub.getModels()
        return context.toGetResponse()
    }

    fun searchSchools(context: SchoolContext, request: SearchSchoolRequest): SearchSchoolResponse {
        context.setQuery(request)
        context.responseSchoolList = SchoolStub.getModels()
        return context.toSearchResponse()
    }

    fun deleteSchool(context: SchoolContext, request: DeleteSchoolRequest): DeleteSchoolResponse {
        context.setQuery(request)
        context.responseSchool = SchoolStub.getModel()
        return context.toDeleteResponse()
    }

    fun initSchool(context: SchoolContext, request: InitSchoolRequest): InitSchoolResponse {
        context.setQuery(request)
        context.responseSchool = SchoolStub.getModel()
        return context.toInitResponse()
    }

    fun errorSchool(context: SchoolContext, e: Throwable): BaseMessage {
        context.addError(e)
        return context.toGetResponse()
    }
}