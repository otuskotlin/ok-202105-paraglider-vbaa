package ru.kotlin.paraglider.vbaa.be.common.models

data class PaginatedModel(
    var size: Int = Int.MIN_VALUE,
    var lastId: SchoolIdModel = SchoolIdModel.NONE,
    var position: PositionModel = PositionModel.NONE,
) {
    enum class PositionModel {
        NONE,
        FIRST,
        MIDDLE,
        LAST;
    }
}
