package ru.kotlin.paraglider.vbaa.be.logics.validation

interface IValidator<T> {
    fun validate(context: T)
}