package ru.kotlin.paraglider.vbaa.be.stubs

import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.openapi.models.*

object SchoolStub {
    private val createSchoolRequest = CreateSchoolRequest(
        messageType = "CreateSchoolRequest",
        requestId = "uuid123",
        createSchool = SchoolDTO(
            name = "MyNebo",
            shortInfo = "Flying school in Moscow area",
            welcomeVideoUrl = "someUrl.com",
            headOfSchool = "uuid-456",
            contactInfo = ContactInfoDTO(
                mobilePhones = listOf("89660000000"),
                socialMedia = listOf("someVkUrl", "@my_nebo.ru"),
                email = "someEmail@ya.ru"
            ),
            location = LocationDTO(
                address = "Moscow area, Voronovo",
                geolocation = "55.00, 44.00",
                shortInfo = "Turn to the left after Cafe"
            ),
            services = emptySet(),
            instructors = emptySet(),
            status = SchoolStatusDTO.ACTIVE
        ),
        debug = BaseDebugRequest(
            mode = BaseDebugRequest.Mode.STUB
        )
    )

    private val schoolStubOne = SchoolModel(
        id = SchoolIdModel(id = "123"),
        name = "MyNebo",
        welcomeVideoUrl = "someUrl.com",
        headOfSchool = UserIdModel(id = "uuid-456"),
        shortInfo = "Flying school in Moscow area",
        location = LocationModel(
            address = "Moscow area, Voronovo",
            geolocation = "55.00, 44.00",
            shortInfo = "Turn to the left after Cafe"
        ),
        instructors = emptySet(),
        contactInfo = ContactInfoModel(
            mobilePhones = listOf("89660000000"),
            socialMedia = listOf("someVkUrl", "@my_nebo.ru"),
            email = "someEmail@ya.ru"
        ),
        services = emptySet(),
        status = SchoolStatusModel.ACTIVE,
        permissions = mutableSetOf(PermissionModel.READ)
    )

    private val schoolStubTwo = SchoolModel(
        id = SchoolIdModel(id = "444"),
        name = "Infinity Sky",
        welcomeVideoUrl = "someUrl.com",
        headOfSchool = UserIdModel(id = "uuid-111"),
        shortInfo = "Paragliding school in Moscow",
        location = LocationModel(
            address = "Moscow area, Letovo",
            geolocation = "51.00, 41.00",
            shortInfo = "8 km from MKAD"
        ),
        instructors = emptySet(),
        contactInfo = ContactInfoModel(
            mobilePhones = listOf("89331112233"),
            socialMedia = listOf("someVkUrl", "@fly_paragliding"),
            email = "someEmail@ya.ru"
        ),
        services = emptySet(),
        status = SchoolStatusModel.ACTIVE,
        permissions = mutableSetOf(PermissionModel.READ)
    )

    fun getModel(model: (SchoolModel.() -> Unit)? = null) = schoolStubOne.copy().also { stub ->
        model?.let { stub.apply(it) }
    }

    fun getCreateSchoolRequest(model: (CreateSchoolRequest.() -> Unit)? = null) = createSchoolRequest.copy().also { stub ->
        model?.let { stub.apply(it) }
    }

    fun getModels() = mutableListOf(
        schoolStubOne.copy(),
        schoolStubTwo.copy()
    )

    val responseSchoolStubOne = ResponseSchool(
        name = schoolStubOne.name,
        welcomeVideoUrl = schoolStubOne.welcomeVideoUrl,
        headOfSchool = schoolStubOne.headOfSchool.asString(),
        shortInfo = schoolStubOne.shortInfo,
        location = LocationDTO(
            address = schoolStubOne.location.address,
            geolocation = schoolStubOne.location.geolocation,
            shortInfo = schoolStubOne.location.shortInfo
        ),
        instructors = emptySet(),
        contactInfo = ContactInfoDTO(
            mobilePhones = schoolStubOne.contactInfo.mobilePhones,
            socialMedia = schoolStubOne.contactInfo.socialMedia,
            email = schoolStubOne.contactInfo.email
        ),
        services = emptySet(),
        status = SchoolStatusDTO.valueOf(schoolStubOne.status.toString()),
        schoolId = schoolStubOne.id.asString(),
        permissions = schoolStubOne.permissions.map { CommonPermissions.valueOf(it.toString())}.toSet()
    )
}