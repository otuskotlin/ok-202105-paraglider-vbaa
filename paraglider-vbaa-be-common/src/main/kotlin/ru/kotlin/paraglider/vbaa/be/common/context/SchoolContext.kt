package ru.kotlin.paraglider.vbaa.be.common.context

import ru.kotlin.paraglider.vbaa.be.common.models.*
import java.time.Instant

data class SchoolContext(
    override var startTime: Instant = Instant.MIN,
    override var operation: CommonOperations = CommonOperations.NONE,
    override var onRequest: String = "",
    override var stubCase: CommonStubCase = CommonStubCase.NONE,

    override val errors: MutableList<IError> = mutableListOf(),
    override var status: CorStatus = CorStatus.NONE,

    var requestSchoolIds: Set<SchoolIdModel> = mutableSetOf(),
    var requestSchool: SchoolModel = SchoolModel(),
    var requestPage: PaginatedModel = PaginatedModel(),
    var responsePage: PaginatedModel = PaginatedModel(),
    var responseSchool: SchoolModel = SchoolModel(),
    var responseSchoolList: MutableList<SchoolModel> = mutableListOf(),
): AbstractContext(
    startTime = startTime,
    operation = operation,
    onRequest = onRequest,
    stubCase = stubCase,
    errors = errors,
    status = status
)