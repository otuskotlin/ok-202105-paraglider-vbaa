package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolCreateStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolDeleteStub
import ru.kotlin.paraglider.vbaa.be.logics.workers.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.checkOperationWorker

object SchoolDelete: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.DELETE,
    )
    chainInitWorker(title = "Инициализация чейна")

    schoolDeleteStub(title = "Обработка стабкейса для Delete")

    // TODO: продовая логика, работа с БД

    prepareResponseChain(title = "Подготовка ответа")
}).build()