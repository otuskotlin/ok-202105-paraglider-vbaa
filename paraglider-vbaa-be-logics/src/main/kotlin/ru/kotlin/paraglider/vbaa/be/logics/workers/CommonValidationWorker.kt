package ru.kotlin.paraglider.vbaa.be.logics.workers

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.validation.IContextValidator

fun ICorChainDsl<out AbstractContext>.validation(
    title: String = "",
    validator: IContextValidator<AbstractContext>
) = worker {
    this.title = title
    on {
        status == CorStatus.RUNNING
    }
    handle {
        validator.validate(this)
    }
}