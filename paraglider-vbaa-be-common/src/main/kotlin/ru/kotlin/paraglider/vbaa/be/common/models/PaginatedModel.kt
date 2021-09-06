package ru.kotlin.paraglider.vbaa.be.common.models

data class PaginatedModel(
    var size: Int = Int.MIN_VALUE,
    var lastId: SchoolIdModel = SchoolIdModel.NONE,
)
