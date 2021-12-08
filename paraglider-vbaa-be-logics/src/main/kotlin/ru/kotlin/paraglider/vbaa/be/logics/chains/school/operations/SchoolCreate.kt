package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolCreateValidator
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolCreateStub
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.*
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chooseDb
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.validation
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolCreate

object SchoolCreate: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.CREATE,
    )
    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    schoolCreateStub(title = "create school stub")
    validation(
        title = "validate create school request",
        validator = SchoolCreateValidator
    )

    chainPermissions("Вычисление разрешений для пользователя")
    worker {
        title = "Инициализация dbSchool"
        on { status == CorStatus.RUNNING }
        handle {
            dbSchoolList[0].headOfSchool = principal.id
        }
    }

    accessValidation("Вычисление прав доступа")
    prepareSchoolForSaving("Подготовка объекта для сохранения")
    repoSchoolCreate(title = "DB: create school")

    frontPermissions(title = "Вычисление пользовательских разрешений для фронтенда")

    prepareResponseChain(title = "Подготовка ответа")
}).build()
