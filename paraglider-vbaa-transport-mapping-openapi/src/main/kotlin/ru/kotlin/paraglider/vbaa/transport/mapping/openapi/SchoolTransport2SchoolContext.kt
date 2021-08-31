package ru.kotlin.paraglider.vbaa.transport.mapping.openapi

import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.openapi.models.*
import java.time.Instant
import java.time.LocalDate

fun SchoolContext.setQuery(query: InitSchoolRequest) = apply {
    operation = CommonOperations.INIT
    onRequest = query.requestId?:""
}
fun SchoolContext.setQuery(query: CreateSchoolRequest) = apply {
    operation = CommonOperations.CREATE
    onRequest = query.requestId?:""
    requestSchool = query.createSchool?.toModel()?: SchoolModel()
}

fun SchoolContext.setQuery(query: GetSchoolRequest) = apply {
    operation = CommonOperations.READ
    onRequest = query.requestId?:""
    requestSchoolIds = query.schoolIdList?.map { value -> SchoolIdModel(value) }?.toSet()?: mutableSetOf()
}

fun SchoolContext.setQuery(query: UpdateSchoolRequest) = apply {
    operation = CommonOperations.UPDATE
    onRequest = query.requestId?:""
    requestSchool = query.updateSchool?.toModel()?: SchoolModel()
}


fun SchoolContext.setQuery(query: DeleteSchoolRequest) = apply {
    operation = CommonOperations.DELETE
    onRequest = query.requestId?:""
    requestSchoolIds = setOf(SchoolIdModel(query.schoolId ?:""))
}

fun SchoolContext.setQuery(query: SearchSchoolRequest) = apply {
    operation = CommonOperations.SEARCH
    onRequest = query.requestId?:""
    requestPage = query.page?.toModel()?: PaginatedModel()
}

fun BasePaginatedRequest.toModel() = PaginatedModel(
    size = size?: 1,
    lastId = SchoolIdModel(lastId?:"")
)

fun UpdateSchool.toModel() = SchoolModel(
    id = SchoolIdModel(schoolId?:""),
    name = name?:"",
    welcomeVideoUrl = welcomeVideoUrl?:"",
    headOfSchool = headOfSchool?.toModel()?: InstructorModel(),
    shortInfo = shortInfo?:"",
    location = location?.toModel()?: LocationModel(),
    instructorList = instructorList?.map { it.toModel() }?: mutableListOf(),
    contactInfo = contactInfo?.toModel()?: ContactInfoModel(),
    serviceBasicInfo = serviceBasicInfo?: mutableListOf(),
    status = status?.let { SchoolStatusModel.valueOf(it.name) }?:SchoolStatusModel.NONE
)

fun SchoolDTO.toModel() = SchoolModel(
    name = name?:"",
    welcomeVideoUrl = welcomeVideoUrl?:"",
    headOfSchool = headOfSchool?.toModel()?: InstructorModel(),
    shortInfo = shortInfo?:"",
    location = location?.toModel()?: LocationModel(),
    instructorList = instructorList?.map { it.toModel() }?: mutableListOf(),
    contactInfo = contactInfo?.toModel()?: ContactInfoModel(),
    serviceBasicInfo = serviceBasicInfo?: mutableListOf(),
    status = status?.let { SchoolStatusModel.valueOf(it.name) }?:SchoolStatusModel.NONE
)

fun ContactInfoDTO.toModel() = ContactInfoModel(
    mobilePhones = mobilePhones?: mutableListOf(),
    socialMedia = socialMedia?: mutableListOf(),
    email = email?:""
)

fun LocationDTO.toModel() = LocationModel(
    address = address?:"",
    geolocation = geolocation?:"",
    shortInfo = shortInfo?:""
)

fun InstructorDTO.toModel() = InstructorModel(
    schoolIdList = schoolIdList?.map { SchoolIdModel(it) }?.toSet()?: mutableSetOf(),
    name = name?:"",
    surname = surname?:"",
    patronymic = patronymic?:"",
    dateOfBirth = LocalDate.parse(dateOfBirth),
    shortInfo = shortInfo?:"",
    photo = photo?:"",
    hoursOfFly = hoursOfFly?:0,
    flyLocations = flyLocations?: mutableSetOf(),
    hasCertificate = hasCertificate?: false,
    certificateUrl = certificateUrl?:"",
    mobilePhone = mobilePhone?:""
)