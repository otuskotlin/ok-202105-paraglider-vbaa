package ru.kotlin.paraglider.vbaa.app.ktor.api.school.auth

import io.ktor.http.*
import org.junit.Test
import ru.kotlin.paraglider.vbaa.app.ktor.api.school.RouterTest
import ru.kotlin.paraglider.vbaa.app.ktor.config.AppKtorConfig
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig
import ru.kotlin.paraglider.vbaa.be.stubs.SchoolStub
import ru.kotlin.paraglider.vbaa.openapi.models.BaseDebugRequest
import ru.kotlin.paraglider.vbaa.openapi.models.GetSchoolRequest
import ru.kotlin.paraglider.vbaa.openapi.models.GetSchoolResponse
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class AuthWrongIssuer : RouterTest() {
    val stubDebug = BaseDebugRequest(mode = BaseDebugRequest.Mode.STUB, stubCase = BaseDebugRequest.StubCase.SUCCESS)
    @Test
    fun authPositiveTest() {
        val data = GetSchoolRequest(schoolIdList = setOf("123"), debug = stubDebug)

        testPostRequest<GetSchoolResponse>(
            body=data,
            uri="/api/v1/school/list",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy()
            )
        ) {
            assertEquals(GetSchoolResponse.Result.SUCCESS, result)
            assertNull(errors)
            assertEquals(SchoolStub.responseSchoolStubOne, readSchoolList?.get(0))
        }
    }
    @Test
    fun authWrongIssuerTest() {
        val data = GetSchoolRequest(schoolIdList = setOf("123"), debug = stubDebug)

        testPostRequest<GetSchoolResponse>(
            body=data,
            uri="/api/v1/school/list",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy(
                    issuer = "some fake company"
                )
            ),
            result = HttpStatusCode.Unauthorized
        )
    }
    @Test
    fun authWrongSecretTest() {
        val data = GetSchoolRequest(schoolIdList = setOf("123"), debug = stubDebug)

        testPostRequest<GetSchoolResponse>(
            body=data,
            uri="/api/v1/school/list",
            config = AppKtorConfig(
                auth = KtorAuthConfig.TEST.copy(
                    secret = "wrong secret"
                )
            ),
            result = HttpStatusCode.Unauthorized
        )
    }

}
