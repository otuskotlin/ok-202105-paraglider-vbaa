package ru.kotlin.paraglider.vbaa.be.common.models

data class SchoolSearchFilter (
    var searchStr: String = "",
    var name: String = "",
    var location: LocationModel = LocationModel(),
    var contactInfo: ContactInfoModel = ContactInfoModel(),
    var searchTypes: MutableSet<CommonSearchTypes> = mutableSetOf(),
)