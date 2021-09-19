package ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators

import io.konform.validation.Validation
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.logics.validation.IContextValidator
import ru.kotlin.paraglider.vbaa.be.logics.validation.mapErrorResult

object SchoolGetValidator : IContextValidator<AbstractContext> {
    override fun validate(context: AbstractContext) {
        if (context is SchoolContext) {
            val validateCreateSchool = Validation<SchoolContext> {
                run(requestSchoolIdsValidation())
            }
            val validationResult = validateCreateSchool(context)
            mapErrorResult(context, validationResult.errors)
        }
    }
}
