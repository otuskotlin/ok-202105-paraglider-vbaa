package ru.kotlin.paraglider.vbaa.be.logics.chains.school

import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.operations.*

class SchoolCrudFacade {
    suspend fun create(context: SchoolContext) {
        SchoolCreate.exec(context.initSettings())
    }
    suspend fun get(context: SchoolContext) {
        SchoolGet.exec(context.initSettings())
    }
    suspend fun delete(context: SchoolContext) {
        SchoolDelete.exec(context.initSettings())
    }
    suspend fun update(context: SchoolContext) {
        SchoolUpdate.exec(context.initSettings())
    }
    suspend fun search(context: SchoolContext) {
        SchoolSearch.exec(context.initSettings())
    }

    // Метод для установки параметров контекста в чейн, параметры передаются в конструкторе класса
    private fun SchoolContext.initSettings() = apply { }
}