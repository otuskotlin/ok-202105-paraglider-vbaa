package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolCreateStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolSearchStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolCreateValidator
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolSearchValidator
import ru.kotlin.paraglider.vbaa.be.logics.workers.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.validation

object SchoolSearch: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.SEARCH,
    )
    chainInitWorker(title = "Инициализация чейна")

    validation(
        title = "validate create school request",
        validator = SchoolSearchValidator
    )

    schoolSearchStub(title = "Обработка стабкейса для Search")

    // TODO: продовая логика, работа с БД

    prepareResponseChain(title = "Подготовка ответа")
}).build()