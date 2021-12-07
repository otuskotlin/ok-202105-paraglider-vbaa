package ru.kotlin.paraglider.vbaa.be.logics.workers.common

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext

fun ICorChainDsl<SchoolContext>.prepareSchoolForSaving(title: String) {
    worker {
        this.title = title
        description = title
        on { status == CorStatus.RUNNING }
        handle {
            with(dbSchoolList[0]) {
                this.name = requestSchool.name
                this.welcomeVideoUrl = requestSchool.welcomeVideoUrl
                this.contactInfo = requestSchool.contactInfo.copy()
                this.location = requestSchool.location.copy()
                this.shortInfo = requestSchool.shortInfo
                this.headOfSchool = requestSchool.headOfSchool
                this.services = requestSchool.services
                this.instructors = requestSchool.instructors
                this.status = requestSchool.status
            }
        }
    }
}
