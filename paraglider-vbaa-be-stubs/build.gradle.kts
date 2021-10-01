plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":paraglider-vbaa-be-common"))
    implementation(project(":paraglider-vbaa-transport-openapi"))

    testImplementation(kotlin("test-junit"))
}