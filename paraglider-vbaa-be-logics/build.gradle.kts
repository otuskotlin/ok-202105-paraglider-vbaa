plugins {
    kotlin("jvm")
}

dependencies {
    val coroutinesVersion: String by project
    val konformVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("io.konform:konform:$konformVersion")

    implementation(project(":paraglider-vbaa-be-common"))
    implementation(project(":paraglider-vbaa-be-stubs"))
    implementation(project(":paraglider-vbaa-mp-common-cor"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}