package ru.kotlin.paraglider.vbaa.be.common.models

data class TripModel(
    var schoolId: String? = "",
    var location: LocationModel? = LocationModel(),
    var photo: String? = "",
    var videoUrl: String? = "",
    var shortInfo: String? = "",
    var campDates: String? = "",
    var flyDaysCount: Int? = 0,
    var equipmentNeeded: Boolean? = false,
    var campPrice: Int? = 0,
    var equipmentRentalPrice: Int? = 0,
    var hasNoFlyDiscount: Boolean? = false,
    var noFlyDiscount: Int? = 0
)