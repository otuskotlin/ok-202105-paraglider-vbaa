package ru.kotlin.paraglider.vbaa.be.logics.workers.common

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.exception.IllegalOperationException

internal fun ICorChainDsl<out AbstractContext>.checkOperationWorker(
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