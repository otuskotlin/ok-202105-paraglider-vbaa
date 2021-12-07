package ru.kotlin.paraglider.vbaa.be.common.models

data class SchoolModel(
    var id: SchoolIdModel = SchoolIdModel.NONE,
    var name: String = "",
    var welcomeVideoUrl: String = "",
    var shortInfo: String = "",
    var location: LocationModel = LocationModel(),
    var contactInfo: ContactInfoModel = ContactInfoModel(),

    var headOfSchool: UserIdModel = UserIdModel.NONE,
    var instructors: Set<UserIdModel> = mutableSetOf(),
    var services: Set<ServiceIdModel> = mutableSetOf(),

    var status: SchoolStatusModel = SchoolStatusModel.NONE,
    var principalRelations: Set<CommonPrincipalRelations> = emptySet(),
    var permissions: MutableSet<PermissionModel> = mutableSetOf(),
)
