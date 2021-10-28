package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolDeleteStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolDeleteValidator
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chooseDb
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolCreate
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolDelete
import ru.kotlin.paraglider.vbaa.be.logics.workers.validation

object SchoolDelete: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.DELETE,
    )
    chainInitWorker(title = "Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

    validation(
        title = "validate delete school request",
        validator = SchoolDeleteValidator
    )

    schoolDeleteStub(title = "Обработка стабкейса для Delete")

    repoSchoolDelete(title = "DB: delete school")

    prepareResponseChain(title = "Подготовка ответа")
}).build()