package ru.kotlin.paraglider.vbaa.be.common.models

data class SchoolModel(
    var id: SchoolIdModel = SchoolIdModel.NONE,
    var name: String = "",
    var welcomeVideoUrl: String = "",
    var shortInfo: String = "",
    var location: LocationModel = LocationModel(),
    var contactInfo: ContactInfoModel = ContactInfoModel(),

    var headOfSchool: InstructorIdModel = InstructorIdModel.NONE,
    var instructors: Set<InstructorIdModel> = mutableSetOf(),
    var services: Set<ServiceIdModel> = mutableSetOf(),

    var status: SchoolStatusModel = SchoolStatusModel.NONE,
    var permissions: MutableSet<PermissionModel> = mutableSetOf(),
)
