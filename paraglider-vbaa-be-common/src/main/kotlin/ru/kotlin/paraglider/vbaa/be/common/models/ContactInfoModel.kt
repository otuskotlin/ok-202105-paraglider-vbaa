package ru.kotlin.paraglider.vbaa.be.common.models

data class ContactInfoModel(
    var mobilePhones: List<String> = mutableListOf(),
    var socialMedia: List<String> = mutableListOf(),
    var email: String = ""
)
