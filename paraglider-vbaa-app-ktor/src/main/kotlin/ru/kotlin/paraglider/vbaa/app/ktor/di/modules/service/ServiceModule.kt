package ru.kotlin.paraglider.vbaa.app.ktor.di.modules.service

import RepoSchoolInMemory
import SchoolService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import ru.kotlin.paraglider.vbaa.be.common.context.ContextConfig
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.SchoolCrudFacade
import java.time.Duration

fun DI.MainBuilder.serviceModule() {
    bind<SchoolService>() with singleton { SchoolService(instance()) }
    bind<SchoolCrudFacade>() with singleton { SchoolCrudFacade(instance()) }
    bind<ContextConfig>() with singleton { ContextConfig(
        repoProd = RepoSchoolInMemory(initObjects = listOf()),
        repoTest = RepoSchoolInMemory(initObjects = listOf(), ttl = Duration.ofHours(1))
    ) }
}