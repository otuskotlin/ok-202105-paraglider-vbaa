package ru.kotlin.paraglider.vbaa.be.common.models

data class TripModel(
    var schoolId: String = "",
    var location: LocationModel = LocationModel(),
    var photo: String = "",
    var videoUrl: String = "",
    var shortInfo: String = "",
    var campDates: String = "",
    var flyDaysCount: Int = Int.MIN_VALUE,
    var equipmentNeeded: Boolean = false,
    var campPrice: Int = Int.MIN_VALUE,
    var equipmentRentalPrice: Int = Int.MIN_VALUE,
    var hasNoFlyDiscount: Boolean = false,
    var noFlyDiscount: Int = Int.MIN_VALUE
)