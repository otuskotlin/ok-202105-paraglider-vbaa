package ru.kotlin.paraglider.vbaa.be.common.models

import java.util.*

@JvmInline
value class ServiceIdModel(val id: String) {
    constructor(id: UUID) : this(id.toString())

    companion object {
        val NONE = ServiceIdModel("")
    }

    fun asString() = id

    fun asUUID(): UUID = UUID.fromString(id)
}
