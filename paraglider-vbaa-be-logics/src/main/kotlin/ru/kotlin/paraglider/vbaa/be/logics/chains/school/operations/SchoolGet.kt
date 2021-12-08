package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolGetStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolGetValidator
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.*
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chooseDb
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolRead

object SchoolGet: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.READ,
    )
    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    schoolGetStub(title = "Обработка стабкейса для Get")
    validation(
        title = "validate get school request",
        validator = SchoolGetValidator
    )

    chainPermissions("Вычисление разрешений для пользователя")
    repoSchoolRead(title = "DB: read school")
    accessValidation("Вычисление прав доступа")
    worker {
        title = "Подготовка результата к отправке"
        description = title
        on { status == CorStatus.RUNNING }
        handle { responseSchoolList = dbSchoolList }
    }

    frontPermissions(title = "Вычислений разрешений для фронтенда")

    prepareResponseChain(title = "Подготовка ответа")
}).build()