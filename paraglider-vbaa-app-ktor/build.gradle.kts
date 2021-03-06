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

fun DependencyHandler.ktor(module: String, version: String? = ktorVersion): Any =
    "io.ktor:ktor-$module:$version"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(ktor("server-core")) // "io.ktor:ktor-server-core:$ktorVersion"
    implementation(ktor("server-netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"
    implementation(ktor("jackson"))

    implementation(ktor("auth"))
    implementation(ktor("auth-jwt"))

    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:$kodeinDiVersion")

//    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    implementation(kotlin("test-junit"))

    implementation(project(":paraglider-vbaa-be-common"))
    implementation(project(":paraglider-vbaa-be-logics"))
    implementation(project(":paraglider-vbaa-be-service-openapi"))
    implementation(project(":paraglider-vbaa-be-stubs"))
    implementation(project(":paraglider-vbaa-be-repo-inmemory"))
    implementation(project(":paraglider-vbaa-be-repo-cassandra"))
    implementation(project(":paraglider-vbaa-be-repo-test"))
    // logging
    implementation(project(":paraglider-vbaa-be-logging"))

    implementation(project(":paraglider-vbaa-transport-openapi"))
    implementation(project(":paraglider-vbaa-transport-mapping-openapi"))
}