package ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators

import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.validation.IContextValidator
import ru.kotlin.paraglider.vbaa.be.logics.validation.mapErrorResult

object SchoolCreateValidator: IContextValidator<AbstractContext> {
    override fun validate(context: AbstractContext) {
        if (context is SchoolContext) {
            val validateCreateSchool = schoolFieldsValidation()
            val validationResult = validateCreateSchool(context.requestSchool)
            mapErrorResult(context, validationResult.errors)
        }
    }
}