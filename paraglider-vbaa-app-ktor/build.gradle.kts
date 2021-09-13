val ktorVersion: String by project
val logbackVersion: String by project
val kodeinDiVersion: String by project

plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass)
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        maintainer.set("(c) Otus")
        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")

    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:$kodeinDiVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    implementation(kotlin("test-junit"))

    //TODO stubs

    implementation(project(":paraglider-vbaa-be-common"))
    implementation(project(":paraglider-vbaa-transport-openapi"))
    implementation(project(":paraglider-vbaa-transport-mapping-openapi"))
}