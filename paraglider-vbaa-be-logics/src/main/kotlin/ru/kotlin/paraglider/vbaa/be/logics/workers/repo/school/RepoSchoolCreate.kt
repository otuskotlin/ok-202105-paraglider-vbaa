package ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolModelRequest

internal fun ICorChainDsl<SchoolContext>.repoSchoolCreate(title: String)= worker {
    this.title = title
    description = "Data from request are stored in the DB Repository"

    on { status == CorStatus.RUNNING }

    handle {
        val result = schoolRepo.create(DbSchoolModelRequest(school = requestSchool))
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