rootProject.name = "paraglider-vbaa"

pluginManagement {
    val kotlinVersion: String by settings
    val openApiVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion

        id("org.openapi.generator") version openApiVersion
        kotlin("kapt") version kotlinVersion
    }
}

include("m2l2-paraglider-vbaa-testing")
include("paraglider-vbaa-transport-mp")
include("paraglider-vbaa-transport-openapi")
include("paraglider-vbaa-be-common")
include("paraglider-vbaa-transport-mapping-openapi")
