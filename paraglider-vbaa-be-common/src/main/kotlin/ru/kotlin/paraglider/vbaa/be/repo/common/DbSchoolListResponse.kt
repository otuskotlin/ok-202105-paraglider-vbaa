package ru.kotlin.paraglider.vbaa.be.repo.common

import ru.kotlin.paraglider.vbaa.be.common.models.CommonErrorModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolModel

data class DbSchoolListResponse(
    override val result: List<SchoolModel>,
    override val isSuccess: Boolean,
    override val errors: List<CommonErrorModel> = emptyList(),
): IDbResponse<List<SchoolModel>> {
    constructor(result: List<SchoolModel>) : this(result, true)
    constructor(error: CommonErrorModel) : this(emptyList(), false, listOf(error))
}
