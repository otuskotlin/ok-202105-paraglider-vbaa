package ru.kotlin.paraglider.vbaa.be.common.models

data class ContactInfoModel(
    val mobilePhones: List<String>? = mutableListOf(),
    val socialMedia: List<String>? = mutableListOf(),
    val email: String? = ""
)
