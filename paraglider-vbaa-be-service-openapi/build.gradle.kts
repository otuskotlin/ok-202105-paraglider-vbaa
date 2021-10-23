plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":paraglider-vbaa-be-common"))
    implementation(project(":paraglider-vbaa-be-logics"))
    implementation(project(":paraglider-vbaa-be-stubs"))
    implementation(project(":paraglider-vbaa-transport-openapi"))
    implementation(project(":paraglider-vbaa-transport-mapping-openapi"))
}