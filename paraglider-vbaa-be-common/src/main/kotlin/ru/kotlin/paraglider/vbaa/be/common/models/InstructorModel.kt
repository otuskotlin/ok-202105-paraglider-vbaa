package ru.kotlin.paraglider.vbaa.be.common.models

data class InstructorModel(
    val schoolIdList: List<String>? = mutableListOf(),
    val name: String? = "",
    val surname: String? = "",
    val patronymic: String? = "",
    val dateOfBirth: String? = "",
    val shortInfo: String? = "",
    val photo: String? = "",
    val hoursOfFly: Int? = 0,
    val flyLocations: List<String>? = mutableListOf(),
    val hasCertificate: Boolean? = false,
    val certificateUrl: String? = "",
    val mobilePhone: String? = ""
)
