package ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs

import core.ICorChainDsl
import handlers.chain
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.exception.StubNotSetException
import ru.kotlin.paraglider.vbaa.be.common.models.CommonStubCase
import ru.kotlin.paraglider.vbaa.be.stubs.SchoolStub

internal fun ICorChainDsl<SchoolContext>.schoolDeleteStub(title: String) = chain {
    this.title = title
    on {
        status == CorStatus.RUNNING && stubCase != CommonStubCase.NONE
    }
    worker {
        this.title = "success stub case for delete request"
        on { stubCase == CommonStubCase.SUCCESS }
        handle {
            responseSchool = SchoolStub.getModel().copy(id = requestSchoolIds.elementAt(0))
            status = CorStatus.FINISHING
        }
    }
    worker {
        this.title = "processing no stub case"
        on { status == CorStatus.RUNNING }
        handle {
            status = CorStatus.FAILING
            addError(
                e = StubNotSetException(stubCase.name),
            )
        }
    }
}