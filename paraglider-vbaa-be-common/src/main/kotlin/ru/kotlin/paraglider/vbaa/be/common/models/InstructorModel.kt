package ru.kotlin.paraglider.vbaa.be.common.models

import java.time.Instant
import java.time.LocalDate

data class InstructorModel(
    var schoolIdList: Set<SchoolIdModel> = mutableSetOf(),
    var name: String = "",
    var surname: String = "",
    var patronymic: String = "",
    var dateOfBirth: LocalDate = LocalDate.MIN,
    var shortInfo: String = "",
    var photo: String = "",
    var hoursOfFly: Int = Int.MIN_VALUE,
    var flyLocations: Set<String> = mutableSetOf(),
    var hasCertificate: Boolean = false,
    var certificateUrl: String = "",
    var mobilePhone: String = ""
)
