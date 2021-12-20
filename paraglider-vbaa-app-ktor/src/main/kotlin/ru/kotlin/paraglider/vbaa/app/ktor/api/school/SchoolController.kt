package ru.kotlin.paraglider.vbaa.app.ktor.api.school

import SchoolService
import io.ktor.auth.jwt.*
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import ru.kotlin.paraglider.vbaa.app.ktor.mappers.toModel
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.mpLogger
import ru.kotlin.paraglider.vbaa.openapi.models.*

class SchoolController(private val schoolService: SchoolService) {
    suspend fun createSchool(request: CreateSchoolRequest, principal: JWTPrincipal?): CreateSchoolResponse {
        return mpLogger(this::createSchool::class.java).doWithLogging("create-school") {
            val context = SchoolContext(
                principal = principal.toModel()
            )
            return@doWithLogging try {
                schoolService.createSchool(context, request)
            } catch (e: Throwable) {
                withContext(NonCancellable) {
                    schoolService.errorSchool(context, e)
                }
            } as CreateSchoolResponse
        }
    }

    suspend fun updateSchool(request: UpdateSchoolRequest, principal: JWTPrincipal?): UpdateSchoolResponse {
        return mpLogger(this::updateSchool::class.java).doWithLogging("update-school") {
            val context = SchoolContext(
                principal = principal.toModel()
            )
            return@doWithLogging try {
                schoolService.updateSchool(context, request)
            } catch (e: Throwable) {
                withContext(NonCancellable) {
                    schoolService.errorSchool(context, e)
                }
            } as UpdateSchoolResponse
        }
    }

    suspend fun getSchoolList(request: GetSchoolRequest, principal: JWTPrincipal?): GetSchoolResponse {
        return mpLogger(this::getSchoolList::class.java).doWithLogging("get-school-list") {
            val context = SchoolContext(
                principal = principal.toModel()
            )
            return@doWithLogging try {
                schoolService.getSchoolList(context, request)
            } catch (e: Throwable) {
                withContext(NonCancellable) {
                    schoolService.errorSchool(context, e)
                }
            } as GetSchoolResponse
        }
    }

    suspend fun searchSchools(request: SearchSchoolRequest, principal: JWTPrincipal?): SearchSchoolResponse {
        return mpLogger(this::searchSchools::class.java).doWithLogging("search-schools") {
            val context = SchoolContext(
                principal = principal.toModel()
            )
            return@doWithLogging try {
                schoolService.searchSchools(context, request)
            } catch (e: Throwable) {
                withContext(NonCancellable) {
                    schoolService.errorSchool(context, e)
                }
            } as SearchSchoolResponse
        }
    }

    suspend fun deleteSchool(request: DeleteSchoolRequest, principal: JWTPrincipal?): DeleteSchoolResponse {
        return mpLogger(this::deleteSchool::class.java).doWithLogging("delete-school") {
            val context = SchoolContext(
                principal = principal.toModel()
            )
            return@doWithLogging try {
                schoolService.deleteSchool(context, request)
            } catch (e: Throwable) {
                withContext(NonCancellable) {
                    schoolService.errorSchool(context, e)
                }
            } as DeleteSchoolResponse
        }
    }

    suspend fun initSchool(request: InitSchoolRequest, principal: JWTPrincipal?): InitSchoolResponse {
        return mpLogger(this::initSchool::class.java).doWithLogging("init-school") {
            val context = SchoolContext(
                principal = principal.toModel()
            )
            return@doWithLogging try {
                schoolService.initSchool(context, request)
            } catch (e: Throwable) {
                schoolService.errorSchool(context, e)
            } as InitSchoolResponse
        }
    }
}

