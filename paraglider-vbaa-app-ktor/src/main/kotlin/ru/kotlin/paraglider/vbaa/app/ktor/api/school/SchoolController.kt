package ru.kotlin.paraglider.vbaa.app.ktor.api.school

import io.ktor.application.*
import io.ktor.util.pipeline.*
import ru.kotlin.paraglider.vbaa.app.ktor.service.SchoolService
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.openapi.models.*
import java.time.Instant

class SchoolController(private val schoolService: SchoolService) {
    fun createSchool(request: CreateSchoolRequest): CreateSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return handleSchool(context) {
            createSchool(context, request)
        } as CreateSchoolResponse
    }

    fun updateSchool(request: UpdateSchoolRequest): UpdateSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return handleSchool(context) {
            updateSchool(context, request)
        } as UpdateSchoolResponse
    }

    fun getSchoolList(request: GetSchoolRequest): GetSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return handleSchool(context) {
            getSchoolList(context, request)
        } as GetSchoolResponse
    }

    fun searchSchools(request: SearchSchoolRequest): SearchSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return handleSchool(context) {
            searchSchools(context, request)
        } as SearchSchoolResponse
    }

    fun deleteSchool(request: DeleteSchoolRequest): DeleteSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return handleSchool(context) {
            deleteSchool(context, request)
        } as DeleteSchoolResponse
    }

    fun initSchool(request: InitSchoolRequest): InitSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return handleSchool(context) {
            initSchool(context, request)
        } as InitSchoolResponse
    }

    private fun handleSchool(context: SchoolContext, function: SchoolService.() -> BaseMessage): BaseMessage =
        schoolService.let {
            return try {
                it.run(function)
            } catch (e: Throwable) {
                it.errorSchool(context, e)
            }
        }
}

