package ru.kotlin.paraglider.vbaa.app.ktor.api.school

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.kotlin.paraglider.vbaa.app.ktor.module
import ru.kotlin.paraglider.vbaa.openapi.models.BaseMessage
import kotlin.test.assertEquals

abstract class RouterTest {
    val mapper = jacksonObjectMapper()

    protected inline fun <reified T> testPostRequest(
        body: BaseMessage? = null,
        uri: String,
        crossinline block: T.() -> Unit
    ) {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Post, uri) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.withCharset(Charsets.UTF_8).toString())
                setBody(mapper.writeValueAsString(body))
            }.apply {
                println(response.content)
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), response.contentType())

                mapper.readValue(response.content, T::class.java).block()
            }
        }
    }
}