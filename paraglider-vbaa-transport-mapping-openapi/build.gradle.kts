plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":paraglider-vbaa-be-common"))
    implementation(project(":paraglider-vbaa-transport-openapi"))

    testImplementation(kotlin("test"))
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")
}
