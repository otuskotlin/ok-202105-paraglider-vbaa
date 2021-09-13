package ru.kotlin.paraglider.vbaa.app.ktor.di.modules.controller

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import ru.kotlin.paraglider.vbaa.app.ktor.api.school.SchoolController

fun DI.MainBuilder.controllerModule() {
    bind<SchoolController>() with singleton { SchoolController(instance()) }
}