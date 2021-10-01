package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolCreateStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolGetStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolCreateValidator
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolGetValidator
import ru.kotlin.paraglider.vbaa.be.logics.workers.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.validation

object SchoolGet: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.READ,
    )
    chainInitWorker(title = "Инициализация чейна")

    validation(
        title = "validate get school request",
        validator = SchoolGetValidator
    )

    schoolGetStub(title = "Обработка стабкейса для Get")

    // TODO: продовая логика, работа с БД

    prepareResponseChain(title = "Подготовка ответа")
}).build()