package ru.kotlin.paraglider.vbaa.be.repo.test

import ru.kotlin.paraglider.vbaa.be.common.models.*
import java.util.*

abstract class BaseInitSchools: IInitObjects<SchoolModel> {

    fun createInitTestModel(
        prefix: String,
        name: String = "$prefix stub name",
        address: String = "$prefix stub address",
        email: String = "$prefix stub email",
    ) = SchoolModel(
        id = SchoolIdModel(UUID.randomUUID()),
        name = name,
        welcomeVideoUrl = "$prefix stub welcomeVideoUrl",
        shortInfo = "$prefix stub shortInfo",
        location = LocationModel(
            address = address,
            geolocation = "$prefix stub geolocation",
            shortInfo = "$prefix stub shortInfo"
        ),
        contactInfo = ContactInfoModel(
            mobilePhones = listOf("$prefix stub mobilePhone 1"),
            socialMedia = listOf("$prefix stub socialMedia 1"),
            email = email
        ),
        headOfSchool = UserIdModel("uuid-123"),
        instructors = emptySet(),
        services = emptySet(),
        status = SchoolStatusModel.ACTIVE
    )
}