package ru.kotlin.paraglider.vbaa.be.repo.common

import ru.kotlin.paraglider.vbaa.be.common.models.CommonErrorModel

interface IDbResponse<T> {
    val isSuccess: Boolean
    val errors: List<CommonErrorModel>
    val result: T
}