package ru.kotlin.paraglider.vbaa.be.common.context

import ru.kotlin.paraglider.vbaa.be.common.models.*
import java.time.Instant

data class SchoolContext(
    var startTime : Instant = Instant.MIN,
    var operation: CommonOperations = CommonOperations.NONE,

    var onRequest: String = "",
    var requestSchoolIds: Set<SchoolIdModel> = mutableSetOf(),
    var requestSchool: SchoolModel = SchoolModel(),
    var responseSchool: SchoolModel = SchoolModel(),
    var requestPage: PaginatedModel = PaginatedModel(),
    var responsePage: PaginatedModel = PaginatedModel(),
    var responseSchoolList: MutableList<SchoolModel> = mutableListOf(),
    val errors: MutableList<IError> = mutableListOf(),
    var status: CorStatus = CorStatus.STARTED,
) {

    fun addError(error: IError, failingStatus: Boolean = true) = apply {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(error)
    }


    fun addError(
        e: Throwable,
        level: IError.Level = IError.Level.ERROR,
        field: String = "",
        failingStatus: Boolean = true
    ) {
        addError(CommonErrorModel(e, field = field, level = level), failingStatus)
    }
}