rootProject.name = "paraglider-vbaa"

pluginManagement {
    val kotlinVersion: String by settings
    val openApiVersion: String by settings
    val bmuschkoVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        kotlin("kapt") version kotlinVersion

        id("org.openapi.generator") version openApiVersion
        id("com.bmuschko.docker-java-application") version bmuschkoVersion
    }
}

include("m2l2-paraglider-vbaa-testing")
include("paraglider-vbaa-transport-mp")
include("paraglider-vbaa-transport-openapi")
include("paraglider-vbaa-be-common")
include("paraglider-vbaa-transport-mapping-openapi")
include("paraglider-vbaa-app-ktor")
include("paraglider-vbaa-mp-common-cor")
include("paraglider-vbaa-be-logics")
include("paraglider-vbaa-be-stubs")
include("paraglider-vbaa-app-kafka")
include("paraglider-vbaa-be-service-openapi")
include("paraglider-vbaa-be-repo-inmemory")
include("paraglider-vbaa-be-repo-test")
include("paraglider-vbaa-be-repo-cassandra")
