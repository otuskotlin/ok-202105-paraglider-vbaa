package ru.kotlin.paraglider.vbaa.be.common.context

import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepo
import java.time.Instant

abstract class AbstractContext(
    open var operation: CommonOperations = CommonOperations.NONE,
    open var principal: CommonPrincipalModel = CommonPrincipalModel.NONE,
    open val chainPermissions: MutableSet<CommonUserPermissions> = mutableSetOf(),
    open var onRequest: String = "",
    open var config: ContextConfig = ContextConfig(),
    open val startTime: Instant = Instant.now(),
    open var stubCase: CommonStubCase = CommonStubCase.NONE,
    open var errors: MutableList<IError> = mutableListOf(),
    open var status: CorStatus = CorStatus.NONE,
    open var workMode: WorkMode = WorkMode.TEST,
) {
    suspend fun addError(error: IError, failingStatus: Boolean = true) = apply {
        if (failingStatus) status = CorStatus.FAILING
        errors.add(error)
    }

    suspend fun addError(
        e: Throwable,
        level: IError.Level = IError.Level.ERROR,
        field: String = "",
        failingStatus: Boolean = true
    ) {
        addError(CommonErrorModel(e, field = field, level = level), failingStatus)
    }
}