package ru.kotlin.paraglider.vbaa.app.ktor

import io.ktor.application.*
import ru.kotlin.paraglider.vbaa.app.ktor.config.AppKtorConfig
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig
import ru.kotlin.paraglider.vbaa.app.ktor.features.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module(config: AppKtorConfig = AppKtorConfig(environment)) {
    installDI()
    installAuth(config.auth)
    installRoutes()
    installCORS()
    installContentNegotiation()
    installCommon()
}
