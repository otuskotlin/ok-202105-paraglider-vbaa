package ru.kotlin.paraglider.vbaa.transport.mapping.openapi

import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.openapi.models.*
import java.net.URL
import java.time.LocalDate

fun SchoolContext.setQuery(query: InitSchoolRequest) = apply {
    operation = CommonOperations.INIT
    onRequest = query.requestId ?: ""
}

fun SchoolContext.setQuery(query: CreateSchoolRequest) = apply {
    operation = CommonOperations.CREATE
    onRequest = query.requestId ?: ""
    requestSchool = query.createSchool?.toModel() ?: SchoolModel()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase?.takeIf { workMode == WorkMode.STUB }.toModel()
}

fun SchoolContext.setQuery(query: GetSchoolRequest) = apply {
    operation = CommonOperations.READ
    onRequest = query.requestId ?: ""
    requestSchoolIds = query.schoolIdList?.map { value -> SchoolIdModel(value) }?.toList() ?: mutableListOf()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase?.takeIf { workMode == WorkMode.STUB }.toModel()
}

fun SchoolContext.setQuery(query: UpdateSchoolRequest) = apply {
    operation = CommonOperations.UPDATE
    onRequest = query.requestId ?: ""
    requestSchool = query.updateSchool?.toModel() ?: SchoolModel()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase?.takeIf { workMode == WorkMode.STUB }.toModel()
}


fun SchoolContext.setQuery(query: DeleteSchoolRequest) = apply {
    operation = CommonOperations.DELETE
    onRequest = query.requestId ?: ""
    requestSchoolIds = listOf(SchoolIdModel(query.schoolId ?: ""))
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase?.takeIf { workMode == WorkMode.STUB }.toModel()
}

fun SchoolContext.setQuery(query: SearchSchoolRequest) = apply {
    operation = CommonOperations.SEARCH
    onRequest = query.requestId ?: ""
    requestPage = query.page?.toModel() ?: PaginatedModel()
    workMode = query.debug?.mode.toModel()
    stubCase = query.debug?.stubCase?.takeIf { workMode == WorkMode.STUB }.toModel()
}

fun BasePaginatedRequest.toModel() = PaginatedModel(
    size = size ?: 1,
    lastId = SchoolIdModel(lastId ?: "")
)

fun UpdateSchool.toModel() = SchoolModel(
    id = SchoolIdModel(schoolId ?: ""),
    name = name ?: "",
    welcomeVideoUrl = welcomeVideoUrl ?: "",
    headOfSchool = headOfSchool?.let { InstructorIdModel(it) } ?: InstructorIdModel.NONE,
    shortInfo = shortInfo ?: "",
    location = location?.toModel() ?: LocationModel(),
    instructors = instructors.orEmpty().map { InstructorIdModel(it) }.toMutableSet(),
    contactInfo = contactInfo?.toModel() ?: ContactInfoModel(),
    services = services.orEmpty().map { ServiceIdModel(it) }.toMutableSet(),
    status = status?.let { SchoolStatusModel.valueOf(it.name) } ?: SchoolStatusModel.NONE
)

fun SchoolDTO.toModel() = SchoolModel(
    name = name ?: "",
    welcomeVideoUrl = welcomeVideoUrl ?: "",
    headOfSchool = headOfSchool?.let { InstructorIdModel(it) } ?: InstructorIdModel.NONE,
    shortInfo = shortInfo ?: "",
    location = location?.toModel() ?: LocationModel(),
    instructors = instructors.orEmpty().map { InstructorIdModel(it) }.toMutableSet(),
    contactInfo = contactInfo?.toModel() ?: ContactInfoModel(),
    services = services.orEmpty().map { ServiceIdModel(it) }.toMutableSet(),
    status = status?.let { SchoolStatusModel.valueOf(it.name) } ?: SchoolStatusModel.NONE
)

fun ContactInfoDTO.toModel() = ContactInfoModel(
    mobilePhones = mobilePhones ?: mutableListOf(),
    socialMedia = socialMedia ?: mutableListOf(),
    email = email ?: ""
)

fun LocationDTO.toModel() = LocationModel(
    address = address ?: "",
    geolocation = geolocation ?: "",
    shortInfo = shortInfo ?: ""
)

fun InstructorDTO.toModel() = InstructorModel(
    schoolIdList = schoolIdList?.map { SchoolIdModel(it) }?.toSet() ?: mutableSetOf(),
    name = name ?: "",
    surname = surname ?: "",
    patronymic = patronymic ?: "",
    dateOfBirth = LocalDate.parse(dateOfBirth ?: "2000-01-01"),
    shortInfo = shortInfo ?: "",
    photo = photo ?: "",
    hoursOfFly = hoursOfFly ?: 0,
    flyLocations = flyLocations ?: mutableSetOf(),
    //TODO fix it cannot initialize empty url -> validate url using regexp
    certificateUrl = URL(certificateUrl ?: "http://someurl.com"),
    mobilePhone = mobilePhone ?: ""
)

private fun BaseDebugRequest.StubCase?.toModel() = when(this) {
    BaseDebugRequest.StubCase.SUCCESS -> CommonStubCase.SUCCESS
    BaseDebugRequest.StubCase.DATABASE_ERROR -> CommonStubCase.DATABASE_ERROR
    null -> CommonStubCase.NONE
}

private fun BaseDebugRequest.Mode?.toModel() = when(this) {
    BaseDebugRequest.Mode.STUB -> WorkMode.STUB
    BaseDebugRequest.Mode.TEST -> WorkMode.TEST
    BaseDebugRequest.Mode.PROD -> WorkMode.PROD
    null -> WorkMode.PROD
}