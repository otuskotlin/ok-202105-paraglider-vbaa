package ru.kotlin.paraglider.vbaa.be.repo.common

import ru.kotlin.paraglider.vbaa.be.common.models.ContactInfoModel
import ru.kotlin.paraglider.vbaa.be.common.models.InstructorModel
import ru.kotlin.paraglider.vbaa.be.common.models.LocationModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolSearchFilter

data class DbSchoolFilterRequest(
    val searchStr: String = "",
    val name: String = "",
    val location: LocationModel = LocationModel(),
    val contactInfo: ContactInfoModel = ContactInfoModel()
): IDbRequest {
    companion object {
        val NONE = DbSchoolFilterRequest()
        fun of(sf: SchoolSearchFilter) = DbSchoolFilterRequest(
            searchStr = sf.searchStr,
            name = sf.name,
            location = sf.location,
            contactInfo = sf.contactInfo
        )
    }
}
