package ru.kotlin.paraglider.vbaa.app.ktor.api.school

import SchoolService
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import ru.kotlin.paraglider.vbaa.app.ktor.mappers.toModel
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.openapi.models.*

class SchoolController(private val schoolService: SchoolService) {
    suspend fun createSchool(request: CreateSchoolRequest, principal: JWTPrincipal?): CreateSchoolResponse {
        val context = SchoolContext(
            principal = principal.toModel()
        )
        return try {
            schoolService.createSchool(context, request)
        } catch (e: Throwable) {
            withContext(NonCancellable) {
                schoolService.errorSchool(context, e)
            }
        } as CreateSchoolResponse
    }

    suspend fun updateSchool(request: UpdateSchoolRequest, principal: JWTPrincipal?): UpdateSchoolResponse {
        val context = SchoolContext(
            principal = principal.toModel()
        )
        return try {
            schoolService.updateSchool(context, request)
        } catch (e: Throwable) {
            withContext(NonCancellable) {
                schoolService.errorSchool(context, e)
            }
        } as UpdateSchoolResponse
    }

    suspend fun getSchoolList(request: GetSchoolRequest, principal: JWTPrincipal?): GetSchoolResponse {
        val context = SchoolContext(
            principal = principal.toModel()
        )
        return try {
            schoolService.getSchoolList(context, request)
        } catch (e: Throwable) {
            withContext(NonCancellable) {
                schoolService.errorSchool(context, e)
            }
        } as GetSchoolResponse
    }

    suspend fun searchSchools(request: SearchSchoolRequest, principal: JWTPrincipal?): SearchSchoolResponse {
        val context = SchoolContext(
            principal = principal.toModel()
        )
        return try {
            schoolService.searchSchools(context, request)
        } catch (e: Throwable) {
            withContext(NonCancellable) {
                schoolService.errorSchool(context, e)
            }
        } as SearchSchoolResponse
    }

    suspend fun deleteSchool(request: DeleteSchoolRequest, principal: JWTPrincipal?): DeleteSchoolResponse {
        val context = SchoolContext(
            principal = principal.toModel()
        )
        return try {
            schoolService.deleteSchool(context, request)
        } catch (e: Throwable) {
            withContext(NonCancellable) {
                schoolService.errorSchool(context, e)
            }
        } as DeleteSchoolResponse
    }

    suspend fun initSchool(request: InitSchoolRequest, principal: JWTPrincipal?): InitSchoolResponse {
        val context = SchoolContext(
            principal = principal.toModel()
        )
        return try {
            schoolService.initSchool(context, request)
        } catch (e: Throwable) {
            schoolService.errorSchool(context, e)
        } as InitSchoolResponse
    }
}

