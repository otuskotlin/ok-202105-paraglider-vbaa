package ru.kotlin.paraglider.vbaa.be.common.context

import ru.kotlin.paraglider.vbaa.be.common.models.*
import java.time.Instant

abstract class AbstractContext(
    open var operation: CommonOperations,
    open var onRequest: String,
) {
    var startTime: Instant = Instant.now()
    var stubCase: CommonStubCase = CommonStubCase.NONE
    var errors: MutableList<IError> = mutableListOf()
    var status: CorStatus = CorStatus.NONE

    private fun addError(error: IError, failingStatus: Boolean = true) = apply {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(error)
    }

    fun addError(
        e: Throwable,
        level: IError.Level = IError.Level.ERROR,
        field: String = "",
        failingStatus: Boolean = true
    ) {
        addError(CommonErrorModel(e, field = field, level = level), failingStatus)
    }
}