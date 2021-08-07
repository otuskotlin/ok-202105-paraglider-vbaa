rootProject.name = "paraglider-vbaa"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
    }
}

include("paraglider-vbaa-common")
include("m2l2-paraglider-vbaa-testing")
