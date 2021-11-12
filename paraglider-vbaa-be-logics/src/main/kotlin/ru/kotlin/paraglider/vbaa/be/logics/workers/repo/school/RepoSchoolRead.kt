package ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolIdListRequest
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolModelRequest

internal fun ICorChainDsl<SchoolContext>.repoSchoolRead(title: String) = worker {
    this.title = title
    description = "Data from request are stored in the DB Repository"

    on { status == CorStatus.RUNNING }

    handle {
        val result = schoolRepo.read(DbSchoolIdListRequest(idList = requestSchoolIds))
        if (result.isSuccess) {
            responseSchoolList =  result.result.toMutableList()
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}