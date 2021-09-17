plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":paraglider-vbaa-be-common"))
    implementation(project(":paraglider-vbaa-app-ktor"))
    implementation(project(":paraglider-vbaa-mp-common-cor"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}