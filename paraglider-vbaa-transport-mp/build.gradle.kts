plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.openapi.generator")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    jvm()
    js()

    val generationDir = "$buildDir/generated"

    sourceSets {
        val serializationVersion: String by project

        val commonMain by getting {
            kotlin.srcDirs("$generationDir/src/commonMain/kotlin")
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-js"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
    }

    openApiGenerate {
        val openapiGroup = "${rootProject.group}.kmp.transport"
        generatorName.set("kotlin")
        library.set("multiplatform")
        outputDir.set(generationDir)
        packageName.set(openapiGroup)
        apiPackage.set("$openapiGroup.api")
        modelPackage.set("$openapiGroup.models")
        invokerPackage.set("$openapiGroup.invoker")
        inputSpec.set("$rootDir/specs/spec-paraglider.yaml")

        /**
         * ?????????? ??????????????????, ?????? ?????? ?????????? ???????????? ????????????, ?????? ?????????????????? ???? ??????????
         */
        globalProperties.apply {
            put("models", "")
            put("modelDocs", "false")
        }

        /**
         * ?????????????????? ???????????????????????????? ???????????????????? ???? ???????????????????????? ???? ????????????????????
         * https://github.com/OpenAPITools/openapi-generator/blob/master/docs/generators/kotlin.md
         */
        configOptions.set(
            mapOf(
                "dateLibrary" to "string",
                "enumPropertyNaming" to "UPPERCASE",
                "collectionType" to "list"
            )
        )
    }
    tasks {
        compileKotlinMetadata {
            dependsOn(openApiGenerate)
        }
    }
}

