package ru.kotlin.paraglider.vbaa.be.repo.common

import ru.kotlin.paraglider.vbaa.be.common.models.SchoolModel

data class DbSchoolModelRequest(
    val school: SchoolModel
): IDbRequest
