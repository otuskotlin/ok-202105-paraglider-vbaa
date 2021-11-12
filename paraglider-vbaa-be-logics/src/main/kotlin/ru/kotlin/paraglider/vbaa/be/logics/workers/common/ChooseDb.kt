package ru.kotlin.paraglider.vbaa.be.logics.workers.common

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.CommonStubCase
import ru.kotlin.paraglider.vbaa.be.common.models.WorkMode
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepo
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool

internal fun ICorChainDsl<SchoolContext>.chooseDb(title: String) = worker {
    this.title = title
    description = """
        Here we choose either prod or test DB repository. 
        In case of STUB request here we use empty repo and set stubCase=SUCCESS if unset
    """.trimIndent()

    handle {
        schoolRepo = when(workMode) {
            WorkMode.PROD -> config.repoProd as IRepoSchool
            WorkMode.TEST -> config.repoTest as IRepoSchool
            WorkMode.STUB -> {
                if (stubCase == CommonStubCase.NONE) {
                    stubCase = CommonStubCase.SUCCESS
                }
                IRepoSchool.NONE
            }
        }
    }

}