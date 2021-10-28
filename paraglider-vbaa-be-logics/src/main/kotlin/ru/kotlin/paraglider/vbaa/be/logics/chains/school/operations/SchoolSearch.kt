package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolSearchStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolSearchValidator
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chooseDb
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolCreate
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolSearch
import ru.kotlin.paraglider.vbaa.be.logics.workers.validation

object SchoolSearch: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.SEARCH,
    )
    chainInitWorker(title = "Инициализация чейна")

    chooseDb(title = "Выбираем БД или STUB")

    validation(
        title = "validate search school request",
        validator = SchoolSearchValidator
    )

    schoolSearchStub(title = "Обработка стабкейса для Search")

    repoSchoolSearch(title = "DB: search school")

    prepareResponseChain(title = "Подготовка ответа")
}).build()