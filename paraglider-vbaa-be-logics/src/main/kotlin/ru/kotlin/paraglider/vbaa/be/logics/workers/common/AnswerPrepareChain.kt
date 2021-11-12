package ru.kotlin.paraglider.vbaa.be.logics.workers.common

import core.ICorChainDsl
import handlers.chain
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext

internal fun ICorChainDsl<out AbstractContext>.prepareResponseChain(title: String) = chain {
    this.title = title
    description = "Чейн считается успешным, если в нем не было ошибок и он отработал"
    worker {
        this.title = "Обработчик успешного чейна"
        on { status in setOf(CorStatus.RUNNING, CorStatus.FINISHING) }
        handle {
            status = CorStatus.SUCCESS
        }
    }
    worker {
        this.title = "Обработчик неуспешного чейна"
        on { status != CorStatus.SUCCESS }
        handle {
            status = CorStatus.ERROR
        }
    }
}