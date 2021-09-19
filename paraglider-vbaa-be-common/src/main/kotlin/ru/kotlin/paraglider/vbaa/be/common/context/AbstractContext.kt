package ru.kotlin.paraglider.vbaa.be.common.context

import ru.kotlin.paraglider.vbaa.be.common.models.*
import java.time.Instant

abstract class AbstractContext(
    open var startTime: Instant,
    open var operation: CommonOperations,
    open var onRequest: String,
    open var stubCase: CommonStubCase,

    open val errors: MutableList<IError>,
    open var status: CorStatus,
) {

    fun addError(error: IError, failingStatus: Boolean = true) = apply {
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