package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolUpdateStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolUpdateValidator
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.*
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chooseDb
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolRead
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolUpdate

object SchoolUpdate: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.UPDATE,
    )
    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    schoolUpdateStub(title = "Обработка стабкейса для Update")
    validation(
        title = "validate update school request",
        validator = SchoolUpdateValidator
    )

    chainPermissions("Вычисление разрешений для пользователя")
    worker(title = "инициализируем requestSchoolIds") { requestSchoolIds = listOf(requestSchool.id) }
    repoSchoolRead(title = "Чтение объекта из БД")
    accessValidation("Вычисление прав доступа")
    prepareSchoolForSaving("Подготовка объекта для сохранения")
    repoSchoolUpdate(title = "DB: update school")

    frontPermissions(title = "Вычисление пользовательских разрешений для фронтенда")
    prepareResponseChain(title = "Подготовка ответа")
}).build()