package ru.kotlin.paraglider.vbaa.be.common.models

import java.util.*

@JvmInline
value class UserIdModel(val id: String) {
    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = UserIdModel("")
    }

    fun asString() = id

    fun asUUID(): UUID = UUID.fromString(id)
}