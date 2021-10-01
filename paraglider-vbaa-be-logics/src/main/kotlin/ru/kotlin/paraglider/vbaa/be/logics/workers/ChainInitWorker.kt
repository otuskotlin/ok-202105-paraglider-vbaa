package ru.kotlin.paraglider.vbaa.be.logics.workers

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext

internal fun ICorChainDsl<out AbstractContext>.chainInitWorker(title: String) = worker {
    this.title = title
    description = "При старте обработки цепочки, статус еще не установлен. Проверяем его"

    on {
        status == CorStatus.NONE
    }
    handle {
        status = CorStatus.RUNNING
    }
}