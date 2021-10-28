package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolUpdateStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolUpdateValidator
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chooseDb
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolCreate
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolUpdate
import ru.kotlin.paraglider.vbaa.be.logics.workers.validation

object SchoolUpdate: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.UPDATE,
    )
    chainInitWorker(title = "Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

    validation(
        title = "validate update school request",
        validator = SchoolUpdateValidator
    )

    schoolUpdateStub(title = "Обработка стабкейса для Update")

    repoSchoolUpdate(title = "DB: update school")

    prepareResponseChain(title = "Подготовка ответа")
}).build()