package ru.kotlin.paraglider.vbaa.be.repo.common

import ru.kotlin.paraglider.vbaa.be.common.models.CommonErrorModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolModel

data class DbSchoolResponse(
    override val result: SchoolModel?,
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
): IDbResponse<SchoolModel?> {
    constructor(result: SchoolModel) : this(result, true)
    constructor(error: CommonErrorModel) : this(null, false, listOf(error))
}