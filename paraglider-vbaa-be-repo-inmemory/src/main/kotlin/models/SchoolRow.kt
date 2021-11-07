package models

import ru.kotlin.paraglider.vbaa.be.common.models.*
import java.io.Serializable

//Добававление инструкторов отдельный CRUD headofSchool, instructorList
data class SchoolRow(
    val id: String? = null,
    val name: String? = null,
    val welcomeVideoUrl: String? = null,
    val shortInfo: String? = null,
    val location: LocationRow? = null,
    val contactInfo: ContactInfoRow? = null,
    val headOfSchool: String? = null,
    val instructors: Set<String>? = null,
    val services: Set<String>? = null,
    val status: String? = null
): Serializable {
    constructor(internal: SchoolModel): this(
        id = internal.id.asString().takeIf { it.isNotBlank() },
        name = internal.name.takeIf { it.isNotBlank() },
        welcomeVideoUrl = internal.welcomeVideoUrl.takeIf { it.isNotBlank() },
        shortInfo = internal.shortInfo.takeIf { it.isNotBlank() },
        location = LocationRow(internal.location),
        contactInfo = ContactInfoRow(internal.contactInfo),
        headOfSchool = internal.headOfSchool.takeIf { it != InstructorIdModel.NONE }?.asString(),
        instructors = internal.instructors.takeIf { it.isNotEmpty() }?.map { it.asString() }?.toMutableSet(),
        services = internal.services.map{ it.asString() }.toMutableSet(),
        status = internal.status.toString(),
    )
    fun toInternal(): SchoolModel = SchoolModel(
        id = id?.let { SchoolIdModel(it) } ?: SchoolIdModel.NONE,
        name = name ?: "",
        welcomeVideoUrl = welcomeVideoUrl ?: "",
        shortInfo = shortInfo ?: "",
        location = location?.toInternal() ?: LocationModel(),
        contactInfo = contactInfo?.toInternal() ?: ContactInfoModel(),
        headOfSchool = headOfSchool?.let { InstructorIdModel(it) } ?: InstructorIdModel.NONE,
        instructors = instructors.orEmpty().map { InstructorIdModel(it) }.toMutableSet(),
        services = services.orEmpty().map { ServiceIdModel(it) }.toMutableSet(),
        status = status?.let { SchoolStatusModel.valueOf(it) } ?: SchoolStatusModel.NONE,
    )
}

data class ContactInfoRow(
    val mobilePhones: List<String>? = null,
    val socialMedia: List<String>? = null,
    val email: String? = null
): Serializable {
    constructor(internal: ContactInfoModel): this(
        mobilePhones = internal.mobilePhones.filter { it.isNotBlank() }.toList(),
        socialMedia = internal.socialMedia.filter { it.isNotBlank() }.toList(),
        email = internal.email.takeIf { it.isNotBlank() }
    )

    fun toInternal(): ContactInfoModel = ContactInfoModel(
        mobilePhones?.filter { it.isNotBlank() }?.toList() ?: emptyList(),
        socialMedia?.filter { it.isNotBlank() }?.toList() ?: emptyList(),
        email ?: ""
    )

}

data class LocationRow(
    val address: String? = null,
    val geolocation: String? = null,
    val shortInfo: String? = null
):Serializable {
    constructor(internal: LocationModel): this(
        address = internal.address.takeIf { it.isNotBlank() },
        geolocation = internal.geolocation.takeIf { it.isNotBlank() },
        shortInfo = internal.shortInfo.takeIf { it.isNotBlank() }
    )

    fun toInternal():LocationModel = LocationModel(
        address ?: "",
        geolocation ?: "",
        shortInfo ?: ""
    )
}
