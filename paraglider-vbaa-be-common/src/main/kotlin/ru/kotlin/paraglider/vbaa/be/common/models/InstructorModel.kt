package ru.kotlin.paraglider.vbaa.be.common.models

import java.net.URL
import java.time.LocalDate

data class InstructorModel(
    var schoolIdList: Set<SchoolIdModel> = mutableSetOf(),
    var name: String = "",
    var surname: String = "",
    var patronymic: String = "",
    var dateOfBirth: LocalDate = LocalDate.MIN,
    var shortInfo: String = "",
    //TODO change to class media Object
    var photo: String = "",
    var hoursOfFly: Int = Int.MIN_VALUE,
    var flyLocations: Set<String> = mutableSetOf(),
    var certificateUrl: URL = URL("https://someurl"),
    var mobilePhone: String = ""
)
