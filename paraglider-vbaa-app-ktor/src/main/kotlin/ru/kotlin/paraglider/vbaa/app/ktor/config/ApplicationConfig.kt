package ru.kotlin.paraglider.vbaa.app.ktor.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.routing.*
import ru.kotlin.paraglider.vbaa.app.ktor.api.mainRoute
import ru.kotlin.paraglider.vbaa.app.ktor.di.initDi

fun Application.installDI() {
    initDi()
}

fun Application.installRoutes() {
    routing { mainRoute() }
}

fun Application.installCommon() {
    install(DefaultHeaders)
    install(CallLogging)
    install(StatusPages)
    install(AutoHeadResponse)
}

fun Application.installContentNegotiation() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            writerWithDefaultPrettyPrinter()
        }
    }
}

fun Application.installCORS() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)

        allowSameOrigin = true
        allowCredentials = true
        allowNonSimpleContentTypes = true

        anyHost() //TODO change later to hosts list
    }
}