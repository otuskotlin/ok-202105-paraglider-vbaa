package ru.kotlin.paraglider.vbaa.app.ktor.mappers

import io.ktor.auth.jwt.*
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig.Companion.F_NAME_CLAIM
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig.Companion.ID_CLAIM
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig.Companion.L_NAME_CLAIM
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig.Companion.M_NAME_CLAIM
import ru.kotlin.paraglider.vbaa.be.common.models.CommonPrincipalModel
import ru.kotlin.paraglider.vbaa.be.common.models.CommonUserGroups
import ru.kotlin.paraglider.vbaa.be.common.models.UserIdModel

fun JWTPrincipal?.toModel() = this?.run {
    CommonPrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { UserIdModel(it) } ?: UserIdModel.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
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
