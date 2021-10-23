plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

application {
    mainClass.set("ru.kotlin.paraglider.vbaa.app.kafka.MainKt")
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        maintainer.set("VBAA")
//        ports.set(listOf(8080))
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

dependencies {
    val kafkaVersion: String by project
    val coroutinesVersion: String by project
    val jacksonVersion: String by project

    implementation(kotlin("stdlib"))

    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    implementation(project(":paraglider-vbaa-be-common"))
    implementation(project(":paraglider-vbaa-be-logics"))
    implementation(project(":paraglider-vbaa-be-service-openapi"))
    implementation(project(":paraglider-vbaa-transport-openapi"))
    implementation(project(":paraglider-vbaa-transport-mapping-openapi"))

    testImplementation(kotlin("test-junit"))
}