package ru.kotlin.paraglider.vbaa.app.ktor.di.modules.service

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import ru.kotlin.paraglider.vbaa.app.ktor.service.SchoolService

fun DI.MainBuilder.serviceModule() {
    bind<SchoolService>() with singleton { SchoolService() }
}