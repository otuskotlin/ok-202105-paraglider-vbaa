package ru.kotlin.paraglider.vbaa.app.ktor.config

import io.ktor.application.*

data class KtorAuthConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String,
) {
    constructor(environment: ApplicationEnvironment): this(
        secret = environment.config.property("jwt.secret").getString(),
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        realm = environment.config.property("jwt.realm").getString()
    )

    companion object {
        const val ID_CLAIM = "id"
        const val GROUPS_CLAIM = "groups"
        const val F_NAME_CLAIM = "fname"
        const val M_NAME_CLAIM = "mname"
        const val L_NAME_CLAIM = "lname"

        val TEST = KtorAuthConfig(
                secret = "some-secret",
                issuer = "Paraglider-vbaa",
                audience = "school-users",
                realm = "Access to Schools"
            )
    }
}
