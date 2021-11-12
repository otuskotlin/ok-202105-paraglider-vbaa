package ru.kotlin.paraglider.vbaa.be.repo.common

import ru.kotlin.paraglider.vbaa.be.common.models.SchoolIdModel

data class DbSchoolIdRequest(
    val id: SchoolIdModel
): IDbRequest
