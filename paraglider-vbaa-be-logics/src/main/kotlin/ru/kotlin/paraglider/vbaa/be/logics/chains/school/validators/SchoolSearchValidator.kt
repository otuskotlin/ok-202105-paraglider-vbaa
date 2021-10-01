package ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minItems
import io.konform.validation.jsonschema.minimum
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.PaginatedModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolIdModel
import ru.kotlin.paraglider.vbaa.be.logics.validation.IContextValidator
import ru.kotlin.paraglider.vbaa.be.logics.validation.mapErrorResult
import ru.kotlin.paraglider.vbaa.be.logics.validation.notBlankCheck

object SchoolSearchValidator : IContextValidator<AbstractContext> {
    override fun validate(context: AbstractContext) {
        if (context is SchoolContext) {
            val validateCreateSchool = Validation<SchoolContext> {
                SchoolContext::requestPage {
                    PaginatedModel::lastId required {
                        SchoolIdModel::id {
                            run(notBlankCheck)
                        }
                    }
                    PaginatedModel::size required {
                        minimum(1)
                    }
                }
            }
            val validationResult = validateCreateSchool(context)
            mapErrorResult(context, validationResult.errors)
        }
    }
}
