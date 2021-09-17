package ru.kotlin.paraglider.vbaa.be.logics.workers

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.exception.IllegalOperationException

internal fun ICorChainDsl<SchoolContext>.checkOperationWorker(
    title: String,
    targetOperation: CommonOperations
) = worker {
    this.title = title
    description = "Если в контексте недопустимая операция, то чейн не успешен"
    on { operation != targetOperation }
    handle {
        status = CorStatus.FAILING
        addError(
            e = IllegalOperationException("Expected ${targetOperation.name} but was ${operation.name}")
        )
    }
}