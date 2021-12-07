//package ru.kotlin.paraglider.vbaa.app.ktor.config
//
//import RepoSchoolInMemory
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
//import io.ktor.application.*
//import ru.kotlin.paraglider.vbaa.be.common.context.ContextConfig
//import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
//import java.time.Duration
//
//data class AppKtorConfig constructor(
//    val objectMapper: ObjectMapper = jacksonObjectMapper(),
//    val schoolRepoTest: IRepoSchool = RepoSchoolInMemory(initObjects = listOf()),
//    val schoolRepoProd: IRepoSchool = RepoSchoolInMemory(initObjects = listOf(), ttl = Duration.ofHours(1)),
//    val contextConfig: ContextConfig = ContextConfig(
//        repoProd = schoolRepoProd,
//        repoTest = schoolRepoTest,
//    ),
//    val auth: KtorAuthConfig = KtorAuthConfig.TEST,
//) {
//    constructor(environment: ApplicationEnvironment): this(
//        auth = KtorAuthConfig(environment)
//    )
//}
