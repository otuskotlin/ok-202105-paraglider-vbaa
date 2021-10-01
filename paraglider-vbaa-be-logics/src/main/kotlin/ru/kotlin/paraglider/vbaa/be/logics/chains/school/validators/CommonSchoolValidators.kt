package ru.kotlin.paraglider.vbaa.be.logics.chains.school.validators

import io.konform.validation.Validation
import io.konform.validation.jsonschema.minItems
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.logics.validation.*
import java.time.LocalDate
import java.time.Period

fun schoolFieldsValidation() = Validation<SchoolModel> {
    SchoolModel::welcomeVideoUrl {
        run(urlCheck)
    }
    SchoolModel::name required {
        run(notBlankCheck)
    }
    SchoolModel::headOfSchool {
        //later replace with instructor validation function
        InstructorModel::name required {
            run(personNameCheck)
        }
        InstructorModel::surname required {
            run(personNameCheck)
        }
        InstructorModel::dateOfBirth {
            addConstraint("Возраст должен быть не менее 14 и не более 100 лет", LocalDate.of(1980,1,1).toString()) {
                val age = Period.between(it, LocalDate.now()).years
                age in 14..99
            }
        }
    }
    SchoolModel::location required {
        LocationModel::address {
            run(addressCheck)
        }
    }
    SchoolModel::contactInfo {
        ContactInfoModel::email required {
            run(emailCheck)
        }
    }
}

fun requestSchoolIdsValidation() = Validation<SchoolContext> {
    SchoolContext::requestSchoolIds {
        minItems(1)
    }
    SchoolContext::requestSchoolIds onEach {
        SchoolIdModel::id {
            run(notBlankCheck)
        }
    }
}