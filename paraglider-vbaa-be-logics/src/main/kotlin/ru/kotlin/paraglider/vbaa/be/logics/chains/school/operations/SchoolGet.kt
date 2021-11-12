package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolGetStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolGetValidator
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chooseDb
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolCreate
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolRead
import ru.kotlin.paraglider.vbaa.be.logics.workers.validation

object SchoolGet: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.READ,
    )
    chainInitWorker(title = "Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

    validation(
        title = "validate get school request",
        validator = SchoolGetValidator
    )

    schoolGetStub(title = "Обработка стабкейса для Get")

    repoSchoolRead(title = "DB: read school")

    prepareResponseChain(title = "Подготовка ответа")
}).build()