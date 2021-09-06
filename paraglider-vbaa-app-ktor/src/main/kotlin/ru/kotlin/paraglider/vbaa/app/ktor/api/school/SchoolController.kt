package ru.kotlin.paraglider.vbaa.app.ktor.api.school

import ru.kotlin.paraglider.vbaa.app.ktor.service.SchoolService
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.openapi.models.*
import java.time.Instant

class SchoolController(private val schoolService: SchoolService) {
    fun createSchool(request: CreateSchoolRequest): CreateSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return try {
            schoolService.createSchool(context, request)
        }
        catch (e: Throwable) {
            schoolService.errorSchool(context, e) as CreateSchoolResponse
        }
    }

    fun updateSchool(request: UpdateSchoolRequest): UpdateSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return try {
            schoolService.updateSchool(context, request)
        }
        catch (e: Throwable) {
            schoolService.errorSchool(context, e) as UpdateSchoolResponse
        }
    }

    fun getSchoolList(request: GetSchoolRequest): GetSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return try {
            schoolService.getSchoolList(context, request)
        }
        catch (e: Throwable) {
            schoolService.errorSchool(context, e) as GetSchoolResponse
        }
    }

    fun deleteSchool(request: DeleteSchoolRequest): DeleteSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return try {
            schoolService.deleteSchool(context, request)
        }
        catch (e: Throwable) {
            schoolService.errorSchool(context, e) as DeleteSchoolResponse
        }
    }

    fun initSchool(request: InitSchoolRequest): InitSchoolResponse {
        val context = SchoolContext(
            startTime = Instant.now()
        )
        return try {
            schoolService.initSchool(context, request)
        }
        catch (e: Throwable) {
            schoolService.errorSchool(context, e) as InitSchoolResponse
        }
    }

}