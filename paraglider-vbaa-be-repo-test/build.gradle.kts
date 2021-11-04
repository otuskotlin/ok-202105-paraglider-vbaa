plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))
    implementation(project(":paraglider-vbaa-be-common"))

    api(kotlin("test-junit"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    val logbackVersion: String by project
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}