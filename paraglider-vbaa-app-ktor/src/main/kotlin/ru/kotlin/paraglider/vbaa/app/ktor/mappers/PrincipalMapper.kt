package ru.kotlin.paraglider.vbaa.app.ktor.mappers

import io.ktor.auth.jwt.*
import ru.kotlin.paraglider.vbaa.be.common.models.CommonPrincipalModel
import ru.kotlin.paraglider.vbaa.be.common.models.CommonUserGroups
import ru.kotlin.paraglider.vbaa.be.common.models.UserIdModel

fun JWTPrincipal?.toModel() = this?.run {
    CommonPrincipalModel(
        id = payload.getClaim("id").asString()?.let { UserIdModel(it) } ?: UserIdModel.NONE,
        fname = payload.getClaim("fname").asString() ?: "",
        mname = payload.getClaim("mname").asString() ?: "",
        lname = payload.getClaim("lname").asString() ?: "",
        groups = payload
            .getClaim("groups")
            ?.asList(String::class.java)
            ?.mapNotNull {
                try {
                    CommonUserGroups.valueOf(it)
                } catch (e: Throwable) {
                    null
                }
            }?.toSet() ?: emptySet()
    )
} ?: CommonPrincipalModel.NONE
