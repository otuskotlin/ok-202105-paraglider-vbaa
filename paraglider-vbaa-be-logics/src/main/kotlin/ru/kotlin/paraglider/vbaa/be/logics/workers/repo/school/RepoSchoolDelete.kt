package ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolIdRequest

internal fun ICorChainDsl<SchoolContext>.repoSchoolDelete(title: String)= worker {
    this.title = title
    description = "The requested object will be deleted from the DB Repository"

    on { status == CorStatus.RUNNING }

    handle {
        val result = schoolRepo.delete(DbSchoolIdRequest(id = requestSchoolIds.elementAt(0)))
        val resultValue = result.result
        if (result.isSuccess && resultValue != null) {
            responseSchool =  resultValue
        } else {
            result.errors.forEach {
                addError(it)
            }
        }
    }
}