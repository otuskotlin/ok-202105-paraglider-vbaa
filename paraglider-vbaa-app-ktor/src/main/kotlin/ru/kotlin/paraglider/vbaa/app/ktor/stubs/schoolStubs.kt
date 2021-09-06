package ru.kotlin.paraglider.vbaa.app.ktor.stubs

import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.openapi.models.*
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object SchoolStub {
    private val schoolStubOne = SchoolModel(
        id = SchoolIdModel(id = "123"),
        name = "MyNebo",
        welcomeVideoUrl = "some_url",
        headOfSchool = InstructorModel(
            schoolIdList = setOf(SchoolIdModel(id = "123")),
            name = "Maria",
            surname = "Viklayeva",
            patronymic = "",
            dateOfBirth = LocalDate.of(1984, 12, 31),
            shortInfo = "Paragliding is my life",
            photo = "photo_url",
            hoursOfFly = 1000,
            flyLocations = mutableSetOf(
                "Chegem", "Babadag", "Krusevo",
                "Marokko", "Юца", "Serbia", "Albania", "Alania"
            ),
            certificateUrl = URL("https://my-nebo.ru"),
            mobilePhone = "89661234567"
        ),
        shortInfo = "Flying school in Moscow area",
        location = LocationModel(
            address = "Moscow area, Voronovo",
            geolocation = "55.00, 44.00",
            shortInfo = "Turn to the left after Cafe"
        ),
        instructorList = listOf(),
        contactInfo = ContactInfoModel(
            mobilePhones = listOf("89660000000"),
            socialMedia = listOf("someVkUrl", "@my_nebo.ru"),
            email = "someEmail"
        ),
        serviceBasicInfo = mutableListOf("adjust harness", "straps stretch out"),
        status = SchoolStatusModel.ACTIVE,
        permissions = mutableSetOf(PermissionModel.READ)
    )

    private val schoolStubTwo = SchoolModel(
        id = SchoolIdModel(id = "444"),
        name = "Infinity Sky",
        welcomeVideoUrl = "some_url",
        headOfSchool = InstructorModel(
            schoolIdList = setOf(SchoolIdModel(id = "444")),
            name = "Liza",
            surname = "Demina",
            patronymic = "",
            dateOfBirth = LocalDate.of(1989, 8, 11),
            shortInfo = "Fly Paragliding",
            photo = "photo_url",
            hoursOfFly = 1000,
            flyLocations = mutableSetOf(
                "Chegem", "Babadag", "Krusevo",
                "Marokko", "Юца", "Serbia", "Albania", "Alania"
            ),
            certificateUrl = URL("https://fly-paragliding.ru"),
            mobilePhone = "89110002233"
        ),
        shortInfo = "Paragliding school in Moscow",
        location = LocationModel(
            address = "Moscow area, Letovo",
            geolocation = "51.00, 41.00",
            shortInfo = "8 km from MKAD"
        ),
        instructorList = listOf(),
        contactInfo = ContactInfoModel(
            mobilePhones = listOf("89331112233"),
            socialMedia = listOf("someVkUrl", "@fly_paragliding"),
            email = "someEmail"
        ),
        serviceBasicInfo = mutableListOf("adjust harness", "straps stretch out"),
        status = SchoolStatusModel.ACTIVE,
        permissions = mutableSetOf(PermissionModel.READ)
    )

    fun getModel(model: (SchoolModel.() -> Unit)? = null) = schoolStubOne.also { stub ->
        model?.let { stub.apply(it) }
    }

    fun getModels() = mutableListOf(
        schoolStubOne,
        schoolStubTwo
    )

    val responseSchoolStubOne = ResponseSchool(
        name = schoolStubOne.name,
        welcomeVideoUrl = schoolStubOne.welcomeVideoUrl,
        headOfSchool = InstructorDTO(
            schoolIdList = schoolStubOne.headOfSchool.schoolIdList.map{it.id}.toSet(),
            name = schoolStubOne.headOfSchool.name,
            surname = schoolStubOne.headOfSchool.surname,
            patronymic = null,
            dateOfBirth = schoolStubOne.headOfSchool.dateOfBirth.format(DateTimeFormatter.ISO_DATE),
            shortInfo = schoolStubOne.headOfSchool.shortInfo,
            photo = schoolStubOne.headOfSchool.photo,
            hoursOfFly = schoolStubOne.headOfSchool.hoursOfFly,
            flyLocations = schoolStubOne.headOfSchool.flyLocations,
            certificateUrl = schoolStubOne.headOfSchool.certificateUrl.toString(),
            mobilePhone = schoolStubOne.headOfSchool.mobilePhone
        ),
        shortInfo = schoolStubOne.shortInfo,
        location = LocationDTO(
            address = schoolStubOne.location.address,
            geolocation = schoolStubOne.location.geolocation,
            shortInfo = schoolStubOne.location.shortInfo
        ),
        instructorList = null,
        contactInfo = ContactInfoDTO(
            mobilePhones = schoolStubOne.contactInfo.mobilePhones,
            socialMedia = schoolStubOne.contactInfo.socialMedia,
            email = schoolStubOne.contactInfo.email
        ),
        serviceBasicInfo = schoolStubOne.serviceBasicInfo,
        status = SchoolStatusDTO.valueOf(schoolStubOne.status.toString()),
        schoolId = schoolStubOne.id.id,
        permissions = schoolStubOne.permissions.map { CommonPermissions.valueOf(it.toString())}.toSet()
    )
}