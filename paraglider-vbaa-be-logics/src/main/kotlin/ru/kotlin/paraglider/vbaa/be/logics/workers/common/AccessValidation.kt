package ru.kotlin.paraglider.vbaa.be.logics.workers.common

import core.ICorChainDsl
import handlers.chain
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.CommonErrorModel
import ru.kotlin.paraglider.vbaa.be.logics.chains.helper.AccessTableConditions
import ru.kotlin.paraglider.vbaa.be.logics.chains.helper.accessTable
import ru.kotlin.paraglider.vbaa.be.logics.chains.helper.resolveRelationsTo

fun ICorChainDsl<SchoolContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { status == CorStatus.RUNNING }
    worker("Вычисление отношения школы к принципалу") {
       dbSchoolList.forEach{dbSchool -> dbSchool.principalRelations = dbSchool.resolveRelationsTo(principal)}
    }
    worker("Вычисление доступа к школе") {
        permitted = dbSchoolList.map { dbSchool ->
            dbSchool.principalRelations.flatMap { relation ->
                chainPermissions.map { permission ->
                    AccessTableConditions(
                        operation = operation,
                        permission = permission,
                        relation = relation,
                    )
                }
            }
                .any {
                    accessTable[it] ?: false
                }
        }.reduce{el1, el2 -> el1 && el2}
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            addError(
                CommonErrorModel(message = "User is not allowed to this operation")
            )
        }
    }
}
