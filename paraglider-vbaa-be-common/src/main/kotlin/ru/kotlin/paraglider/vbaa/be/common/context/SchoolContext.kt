package ru.kotlin.paraglider.vbaa.be.common.context

import ru.kotlin.paraglider.vbaa.be.common.models.*
import java.time.Instant

data class SchoolContext(
    override var operation: CommonOperations = CommonOperations.NONE,
    override var onRequest: String = "",

    var requestSchoolIds: Set<SchoolIdModel> = mutableSetOf(),
    var requestSchool: SchoolModel = SchoolModel(),
    var requestPage: PaginatedModel = PaginatedModel(),
    var responsePage: PaginatedModel = PaginatedModel(),
    var responseSchool: SchoolModel = SchoolModel(),
    var responseSchoolList: MutableList<SchoolModel> = mutableListOf(),
): AbstractContext(
    operation = operation,
    onRequest = onRequest,
)