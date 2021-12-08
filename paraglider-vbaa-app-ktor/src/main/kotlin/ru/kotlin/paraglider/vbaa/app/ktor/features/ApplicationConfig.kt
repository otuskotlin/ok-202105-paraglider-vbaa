package ru.kotlin.paraglider.vbaa.app.ktor.features

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.routing.*
import ru.kotlin.paraglider.vbaa.app.ktor.api.mainRoute
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig.Companion.GROUPS_CLAIM
import ru.kotlin.paraglider.vbaa.app.ktor.di.initDi

fun Application.installDI() {
    initDi()
}

fun Application.installRoutes() {
    routing {
        authenticate("auth-jwt") {
            mainRoute()
        }
    }
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

fun Application.installAuth(config: KtorAuthConfig) {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = config.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { jwtCredential: JWTCredential ->
                // Лучше использовать IValidator
                when {
                    !jwtCredential.payload.audience.contains(config.audience) -> {
                        log.error("Unsupported audience in JWT token ${jwtCredential.payload.audience}. Must be ${config.audience}")
                        null
                    }
                    jwtCredential.payload.issuer != config.issuer -> {
                        log.error("Wrong issuer in JWT token ${jwtCredential.payload.issuer}. Must be ${config.issuer}")
                        null
                    }
                    jwtCredential.payload.getClaim(GROUPS_CLAIM).asList(String::class.java).isNullOrEmpty() -> {
                        log.error("Groups claim must not be empty in JWT token")
                        null
                    }
                    else -> JWTPrincipal(jwtCredential.payload)
                }
            }
        }
    }
}