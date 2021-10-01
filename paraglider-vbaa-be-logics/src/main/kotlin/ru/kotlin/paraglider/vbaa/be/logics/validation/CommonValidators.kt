package ru.kotlin.paraglider.vbaa.be.logics.validation

import io.konform.validation.Validation
import io.konform.validation.ValidationErrors
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.minimum
import io.konform.validation.jsonschema.pattern
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.CommonErrorModel

val notBlankCheck = Validation<String> {
    pattern(Regex("(.|\\s)*\\S(.|\\s)*")) hint "Поле не должно быть пустым"
}

val notNumberCheck = Validation<String> {
    pattern(Regex("^[^\\d]+\$")) hint "Поле не должно иметь цифры и спецсимволы"
}

val addressCheck = Validation<String> {
        run(notBlankCheck)
        minLength(10) hint "Адрес должен быть больше 10 символов"
//        pattern(Regex("")) hint "Адрес должен соответствовать формату"
}

val urlCheck = Validation<String> {
        pattern(
            Regex("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
        ) hint "Неверный формат url"
}

val personNameCheck = Validation<String> {
        run(notBlankCheck)
        run(notNumberCheck)
        minLength(2) hint "Имя должно иметь больше 1 символа"
}


val emailCheck = Validation<String> {
    run {
        pattern(Regex("^[a-zA-Z0-9_!#\$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$")) hint "Некорректный формат email"
    }
}


fun mapErrorResult(context: AbstractContext, errors: ValidationErrors) {
    if (!errors.isEmpty()) {
        context.status = CorStatus.FAILING
        context.errors.addAll(
            errors.map { res ->
                CommonErrorModel(
                    field = res.dataPath,
                    message = res.message
                )
            }
        )
    }
}