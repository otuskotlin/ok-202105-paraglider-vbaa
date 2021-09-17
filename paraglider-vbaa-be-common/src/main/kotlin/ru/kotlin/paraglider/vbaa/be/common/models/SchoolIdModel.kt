package ru.kotlin.paraglider.vbaa.be.common.models

@JvmInline
value class SchoolIdModel(private val id: String) {
    companion object {
        val NONE = SchoolIdModel("")
    }

    fun asString() = id
}
