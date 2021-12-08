package ru.kotlin.paraglider.vbaa.app.ktor.api.school.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import ru.kotlin.paraglider.vbaa.app.ktor.config.KtorAuthConfig
import ru.kotlin.paraglider.vbaa.be.stubs.SchoolStub
import java.util.*

fun KtorAuthConfig.Companion.testUserToken(): String = testToken("TEST", "USER")
fun KtorAuthConfig.Companion.testModeratorToken(): String = testToken("TEST", "USER", "MODERATOR")
fun KtorAuthConfig.Companion.testAdminToken(): String = testToken("TEST", "USER", "ADMIN")
private fun testToken(vararg groups: String) = JWT.create()
    .withExpiresAt(
        GregorianCalendar().apply {
            set(2036, 0, 1, 0, 0, 0)
            timeZone = TimeZone.getTimeZone("UTC")
        }.time
    )
    .withAudience(KtorAuthConfig.TEST.audience)
    .withIssuer(KtorAuthConfig.TEST.issuer)
    .withAudience(KtorAuthConfig.TEST.audience)
    .withIssuer(KtorAuthConfig.TEST.issuer)
    .withClaim(KtorAuthConfig.ID_CLAIM, SchoolStub.getModel().id.asString())
    .withClaim(KtorAuthConfig.F_NAME_CLAIM, "Ivan")
    .withClaim(KtorAuthConfig.M_NAME_CLAIM, "I.")
    .withClaim(KtorAuthConfig.L_NAME_CLAIM, "Ivanov")
    .withArrayClaim(KtorAuthConfig.GROUPS_CLAIM, groups)
//            .withExpiresAt(Date(System.currentTimeMillis() + 60000))
    .sign(Algorithm.HMAC256(KtorAuthConfig.TEST.secret))
    .apply { println("Test JWT token: $this") }