plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false

    id("org.openapi.generator") apply false
}

group = "ru.kotlin.paraglider.vbaa"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}