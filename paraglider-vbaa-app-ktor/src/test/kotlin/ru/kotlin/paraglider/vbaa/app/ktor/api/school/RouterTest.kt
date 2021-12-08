package ru.kotlin.paraglider.vbaa.app.ktor.api.school

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.kotlin.paraglider.vbaa.app.ktor.api.school.auth.testUserToken
import ru.kotlin.paraglider.vbaa.app.ktor.config.AppKtorConfig
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig
import ru.kotlin.paraglider.vbaa.app.ktor.module
import ru.kotlin.paraglider.vbaa.openapi.models.BaseMessage
import kotlin.test.assertEquals

abstract class RouterTest {
    protected inline fun <reified T> testPostRequest(
        body: BaseMessage? = null,
        uri: String,
        config: AppKtorConfig = AppKtorConfig(),
        result: HttpStatusCode = HttpStatusCode.OK,
        crossinline block: T.() -> Unit = {}
    ) {
        withTestApplication({
            module(config = config)
        }) {
            handleRequest(HttpMethod.Post, uri) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                addHeader(HttpHeaders.Authorization, "Bearer ${KtorAuthConfig.testUserToken()}")
                setBody(config.objectMapper.writeValueAsString(body))
            }.apply {
                println(response.content)
                assertEquals(result, response.status())
                if (result == HttpStatusCode.OK) {
                    assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())
                    config.objectMapper.readValue(response.content, T::class.java).block()
                }
            }
        }
    }
}