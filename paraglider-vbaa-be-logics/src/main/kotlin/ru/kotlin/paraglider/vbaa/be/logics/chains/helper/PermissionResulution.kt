package ru.kotlin.paraglider.vbaa.be.logics.chains.helper

import ru.kotlin.paraglider.vbaa.be.common.models.CommonPrincipalModel
import ru.kotlin.paraglider.vbaa.be.common.models.CommonPrincipalRelations
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolStatusModel

fun SchoolModel.resolveRelationsTo(principal: CommonPrincipalModel): Set<CommonPrincipalRelations> = listOf(
    CommonPrincipalRelations.NONE,
    CommonPrincipalRelations.OWN.takeIf { principal.id == headOfSchool || instructors.contains(principal.id)},
    CommonPrincipalRelations.PUBLIC,
    //TODO понять какое условие необходимо для перехода в это отношение
    CommonPrincipalRelations.MODERATABLE.takeIf { status == SchoolStatusModel.ACTIVE },
).filterNotNull().toSet()
