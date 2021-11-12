package ru.kotlin.paraglider.vbaa.be.common.context

import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolFilterRequest
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool

data class SchoolContext(
    override var operation: CommonOperations = CommonOperations.NONE,
    override var onRequest: String = "",
    override var workMode: WorkMode = WorkMode.PROD,

    override var status: CorStatus = CorStatus.NONE,
    override var stubCase: CommonStubCase = CommonStubCase.NONE,

    var schoolRepo: IRepoSchool = IRepoSchool.NONE,

    var requestFilter: DbSchoolFilterRequest = DbSchoolFilterRequest(),

    var requestSchoolIds: List<SchoolIdModel> = mutableListOf(),
    var requestSchool: SchoolModel = SchoolModel(),
    var requestPage: PaginatedModel = PaginatedModel(),

    var responsePage: PaginatedModel = PaginatedModel(),
    var responseSchool: SchoolModel = SchoolModel(),
    var responseSchoolList: List<SchoolModel> = mutableListOf(),
): AbstractContext()