package ru.kotlin.paraglider.vbaa.app.ktor.api.school

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.kotlin.paraglider.vbaa.app.ktor.mappers.toModel

fun Route.SchoolRoute() {
    val controller by closestDI().instance<SchoolController>()

    post("/create") {
        val response = controller.createSchool(call.receive(), call.principal())
        call.respond(response)
    }
    post("/update") {
        val response = controller.updateSchool(call.receive(), call.principal())
        call.respond(response)
    }
    post("/list") {
        val response = controller.getSchoolList(call.receive(), call.principal())
        call.respond(response)
    }
    post("/search") {
        val response = controller.searchSchools(call.receive(), call.principal())
        call.respond(response)
    }
    post("/delete") {
        val response = controller.deleteSchool(call.receive(), call.principal())
        call.respond(response)
    }
    post("/init") {
        val response = controller.initSchool(call.receive(), call.principal())
        call.respond(response)
    }
}