package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolCreateValidator
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolCreateStub
import ru.kotlin.paraglider.vbaa.be.logics.workers.validation
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chooseDb
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolCreate

object SchoolCreate: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.CREATE,
    )
    chainInitWorker(title = "Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

    validation(
        title = "validate create school request",
        validator = SchoolCreateValidator
    )

    schoolCreateStub(title = "create school stub")

    repoSchoolCreate(title = "DB: create school")

    prepareResponseChain(title = "Подготовка ответа")
}).build()
