package ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators

import io.konform.validation.Validation
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolIdModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolModel
import ru.kotlin.paraglider.vbaa.be.logics.validation.IContextValidator
import ru.kotlin.paraglider.vbaa.be.logics.validation.mapErrorResult
import ru.kotlin.paraglider.vbaa.be.logics.validation.notBlankCheck

object SchoolUpdateValidator : IContextValidator<AbstractContext> {
    override fun validate(context: AbstractContext) {
        if (context is SchoolContext) {
            val validateUpdateSchool = Validation<SchoolModel> {
                SchoolModel::id required {
                    SchoolIdModel::id {
                        run(notBlankCheck)
                    }
                }
                run(schoolFieldsValidation())
            }
            val validationResult = validateUpdateSchool(context.requestSchool)
            mapErrorResult(context, validationResult.errors)
        }
    }
}
