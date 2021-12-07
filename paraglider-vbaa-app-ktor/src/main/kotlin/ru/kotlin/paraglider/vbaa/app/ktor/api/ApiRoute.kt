package ru.kotlin.paraglider.vbaa.app.ktor.api

import io.ktor.auth.*
import io.ktor.routing.*
import ru.kotlin.paraglider.vbaa.app.ktor.api.school.SchoolRoute

fun Route.mainRoute() {
    route("/api/v1") {
        route("/school") { SchoolRoute() }
    }
}