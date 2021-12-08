package ru.kotlin.paraglider.vbaa.be.logics.chains.helper

import ru.kotlin.paraglider.vbaa.be.common.models.*

fun SchoolModel.resolveRelationsTo(principal: CommonPrincipalModel): Set<SchoolPrincipalRelations> = listOf(
    SchoolPrincipalRelations.NONE,
    //all are users
    SchoolPrincipalRelations.USER,
    //TO-BE implemeted student list
//    SchoolPrincipalRelations.STUDENT,
    SchoolPrincipalRelations.INSTRUCTOR.takeIf { instructors.contains(principal.id) },
    SchoolPrincipalRelations.HEAD.takeIf { principal.id == headOfSchool },
    SchoolPrincipalRelations.APP_MODERATOR.takeIf { principal.groups.contains(CommonUserGroups.APP_MODERATOR) }
).filterNotNull().toSet()
