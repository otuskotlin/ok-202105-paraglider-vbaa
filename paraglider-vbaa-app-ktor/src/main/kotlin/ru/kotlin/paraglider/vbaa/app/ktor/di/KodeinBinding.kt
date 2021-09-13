package ru.kotlin.paraglider.vbaa.app.ktor.di

import io.ktor.application.*
import org.kodein.di.ktor.di
import ru.kotlin.paraglider.vbaa.app.ktor.di.modules.controller.controllerModule
import ru.kotlin.paraglider.vbaa.app.ktor.di.modules.service.serviceModule

fun Application.initDi() = di {
    controllerModule()
    serviceModule()
}