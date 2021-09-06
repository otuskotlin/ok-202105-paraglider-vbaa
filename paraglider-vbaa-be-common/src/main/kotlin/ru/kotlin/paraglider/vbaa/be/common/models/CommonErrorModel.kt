package ru.kotlin.paraglider.vbaa.be.common.models

data class CommonErrorModel(
    override val field: String = "",
    override val level: IError.Level = IError.Level.ERROR,
    override val message: String = "",
    override val exception: Throwable = Throwable(),
) : IError {
    constructor(e: Throwable, level: IError.Level = IError.Level.ERROR, field: String = "") : this(
        field = field,
        level = level,
        message = e.message ?: "",
        exception = e
    )
}
