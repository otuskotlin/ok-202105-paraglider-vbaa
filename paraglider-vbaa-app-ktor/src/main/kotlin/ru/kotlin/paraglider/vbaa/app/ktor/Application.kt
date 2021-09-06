package ru.kotlin.paraglider.vbaa.app.ktor

import io.ktor.application.*
import ru.kotlin.paraglider.vbaa.app.ktor.config.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    installDI()
    installRoutes()
    installCORS()
    installContentNegotiation()
    installCommon()
}
