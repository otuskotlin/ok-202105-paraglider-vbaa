package ru.kotlin.paraglider.vbaa.app.ktor.api.school

import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Route.SchoolRoute() {
    post("/create") {
        val controller by closestDI().instance<SchoolController>()
        val response = controller.createSchool(call.receive())
        call.respond(response)
    }
    post("/update") {
        val controller by closestDI().instance<SchoolController>()
        val response = controller.updateSchool(call.receive())
        call.respond(response)
    }
    post("/list") {
        val controller by closestDI().instance<SchoolController>()
        val response = controller.getSchoolList(call.receive())
        call.respond(response)
    }
    post("/delete") {
        val controller by closestDI().instance<SchoolController>()
        val response = controller.deleteSchool(call.receive())
        call.respond(response)
    }
    post("/init") {
        val controller by closestDI().instance<SchoolController>()
        val response = controller.initSchool(call.receive())
        call.respond(response)
    }
}