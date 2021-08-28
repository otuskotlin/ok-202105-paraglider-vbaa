package ru.kotlin.paraglider.vbaa.be.common.models

data class SchoolModel(
    var id: SchoolIdModel? = SchoolIdModel.NONE,
    var name: String? = "",
    var welcomeVideoUrl: String? = "",
    var headOfSchool: InstructorModel? = InstructorModel(),
    var shortInfo: String? = "",
    var location: LocationModel? = LocationModel(),
    var instructorList: List<InstructorModel>? = mutableListOf(),
    var contactInfo: ContactInfoModel? = ContactInfoModel(),
    var serviceBasicInfo: List<String>? = mutableListOf(),
    var status: SchoolStatusModel? = SchoolStatusModel.NONE,
    var permissions: MutableSet<PermissionModel> = mutableSetOf(),
)
