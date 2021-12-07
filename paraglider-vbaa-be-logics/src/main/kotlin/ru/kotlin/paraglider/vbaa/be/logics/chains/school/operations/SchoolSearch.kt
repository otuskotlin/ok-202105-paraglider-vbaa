package ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations

import core.ICorExec
import core.chain
import handlers.*
import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.CommonSearchTypes
import ru.kotlin.paraglider.vbaa.be.common.models.CommonUserPermissions
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.stubs.schoolSearchStub
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators.SchoolSearchValidator
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.*
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chainInitWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.checkOperationWorker
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.chooseDb
import ru.kotlin.paraglider.vbaa.be.logics.workers.common.prepareResponseChain
import ru.kotlin.paraglider.vbaa.be.logics.workers.repo.school.repoSchoolSearch

object SchoolSearch: ICorExec<SchoolContext> by chain<SchoolContext>({
    checkOperationWorker(
        title = "Проверка операции",
        targetOperation = CommonOperations.SEARCH,
    )
    chainInitWorker(title = "Инициализация чейна")
    chooseDb(title = "Выбираем БД или STUB")
    schoolSearchStub(title = "Обработка стабкейса для Search")
    validation(
        title = "validate search school request",
        validator = SchoolSearchValidator
    )


    chainPermissions("Вычисление разрешений для пользователя")
    chain {
        title = "Подготовка поискового запроса"
        description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
        on { status == CorStatus.RUNNING }
        worker {
            title = "Определение типа поиска"
            description = title
            handle {
                dbFilter.searchTypes = listOf(
                    CommonSearchTypes.OWN.takeIf { chainPermissions.contains(CommonUserPermissions.SEARCH_SCHOOL_OWN) },
                    CommonSearchTypes.PUBLIC.takeIf { chainPermissions.contains(CommonUserPermissions.SEARCH_SCHOOL_PUBLIC) },
                ).filterNotNull().toMutableSet()
            }
        }
        worker("Копируем все поля бизнес-поиска") {
            dbFilter.contactInfo = requestFilter.contactInfo
            dbFilter.location = requestFilter.location
            dbFilter.name = requestFilter.name
            dbFilter.searchStr = requestFilter.searchStr
        }
    }

    repoSchoolSearch(title = "DB: search school")

    frontPermissions(title = "Вычисление пользовательских разрешений для фронтенда")

    prepareResponseChain(title = "Подготовка ответа")
}).build()