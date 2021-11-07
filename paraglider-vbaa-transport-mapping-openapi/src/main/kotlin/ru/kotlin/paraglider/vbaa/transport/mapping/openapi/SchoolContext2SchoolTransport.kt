package ru.kotlin.paraglider.vbaa.transport.mapping.openapi

import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.exception.OperationNotSetException
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.openapi.models.*
import java.time.LocalDate


fun SchoolContext.toInitResponse() = InitSchoolResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) InitSchoolResponse.Result.SUCCESS
    else InitSchoolResponse.Result.FAILED
)

fun SchoolContext.toGetResponse() = GetSchoolResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    readSchoolList = responseSchoolList.takeIf { it.isNotEmpty() }?.filter { it != SchoolModel() }
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) GetSchoolResponse.Result.SUCCESS
    else GetSchoolResponse.Result.FAILED
)

fun SchoolContext.toCreateResponse() = CreateSchoolResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    createdSchool = responseSchool.takeIf { it != SchoolModel() }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) CreateSchoolResponse.Result.SUCCESS
    else CreateSchoolResponse.Result.FAILED
)

fun SchoolContext.toUpdateResponse() = UpdateSchoolResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    updatedSchool = responseSchool.takeIf { it != SchoolModel() }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) UpdateSchoolResponse.Result.SUCCESS
    else UpdateSchoolResponse.Result.FAILED
)

fun SchoolContext.toDeleteResponse() = DeleteSchoolResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    deletedSchool = responseSchool.takeIf { it != SchoolModel() }?.toTransport(),
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) DeleteSchoolResponse.Result.SUCCESS
    else DeleteSchoolResponse.Result.FAILED
)

fun SchoolContext.toSearchResponse() = SearchSchoolResponse(
    requestId = onRequest.takeIf { it.isNotBlank() },
    foundSchools = responseSchoolList.takeIf { it.isNotEmpty() }?.filter { it != SchoolModel() }
        ?.map { it.toTransport() },
    errors = errors.takeIf { it.isNotEmpty() }?.map { it.toTransport() },
    result = if (errors.find { it.level == IError.Level.ERROR } == null) SearchSchoolResponse.Result.SUCCESS
    else SearchSchoolResponse.Result.FAILED
)

fun SchoolContext.toResponse() = when (operation) {
    CommonOperations.INIT -> toInitResponse()
    CommonOperations.CREATE -> toCreateResponse()
    CommonOperations.READ -> toGetResponse()
    CommonOperations.UPDATE -> toUpdateResponse()
    CommonOperations.DELETE -> toDeleteResponse()
    CommonOperations.SEARCH -> toSearchResponse()
    CommonOperations.NONE -> throw OperationNotSetException("Operation for error response is not set")
}


private fun IError.toTransport() = RequestError(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
)

private fun SchoolModel.toTransport() = ResponseSchool(
    name = name.takeIf { it.isNotBlank() },
    welcomeVideoUrl = welcomeVideoUrl.takeIf { it.isNotBlank() },
    headOfSchool = headOfSchool.takeIf { it != InstructorIdModel.NONE }?.asString(),
    shortInfo = shortInfo.takeIf { it.isNotBlank() },
    location = location.takeIf { it != LocationModel() }?.toTransport(),
    instructors = instructors.takeIf { it.isNotEmpty() }?.map { it.asString() }?.toMutableSet(),
    contactInfo = contactInfo.takeIf { it != ContactInfoModel() }?.toTransport(),
    services = services.takeIf { it.isNotEmpty() }?.map { it.asString() }?.toMutableSet(),
    status = status.takeIf { it != SchoolStatusModel.NONE }?.let { SchoolStatusDTO.valueOf(it.name) },
    schoolId = id.takeIf { it != SchoolIdModel.NONE }?.asString(),
    permissions = permissions.takeIf { it.isNotEmpty() }?.filter { it != PermissionModel.NONE }
        ?.map { CommonPermissions.valueOf(it.name) }?.toSet()
)


private fun InstructorModel.toTransport() = InstructorDTO(
    schoolIdList = schoolIdList.takeIf { it.isNotEmpty() }?.filter { it != SchoolIdModel.NONE }?.map { it.asString() }
        ?.toSet(),
    name = name.takeIf { it.isNotBlank() },
    surname = surname.takeIf { it.isNotBlank() },
    patronymic = patronymic.takeIf { it.isNotBlank() },
    dateOfBirth = dateOfBirth.takeIf { it != LocalDate.MIN }.toString(),
    shortInfo = shortInfo.takeIf { it.isNotBlank() },
    photo = photo.takeIf { it.isNotBlank() },
    hoursOfFly = hoursOfFly.takeIf { it != Int.MIN_VALUE },
    flyLocations = flyLocations.takeIf { it.isNotEmpty() },
    certificateUrl = certificateUrl.toString(),
    mobilePhone = mobilePhone.takeIf { it.isNotBlank() },
)

private fun LocationModel.toTransport() = LocationDTO(
    address = address.takeIf { it.isNotBlank() },
    geolocation = geolocation.takeIf { it.isNotBlank() },
    shortInfo = shortInfo.takeIf { it.isNotBlank() }
)

private fun ContactInfoModel.toTransport() = ContactInfoDTO(
    mobilePhones = mobilePhones.takeIf { it.isNotEmpty() },
    socialMedia = socialMedia.takeIf { it.isNotEmpty() },
    email = email.takeIf { it.isNotBlank() }
)