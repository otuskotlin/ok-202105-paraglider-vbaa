package ru.kotlin.paraglider.vbaa.be.logics.chains.helper

import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolPrincipalRelations
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolUserPermissions

data class AccessTableConditions(
    val operation: CommonOperations,
    val permission: SchoolUserPermissions,
    val relation: SchoolPrincipalRelations
)
//TODO подумать над реализацией
// более компактной структуры хранения
val accessTable = mapOf(
    // Create
    AccessTableConditions(
        operation = CommonOperations.CREATE,
        permission = SchoolUserPermissions.CREATE_SCHOOL,
        relation = SchoolPrincipalRelations.HEAD
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.CREATE,
        permission = SchoolUserPermissions.CREATE_SCHOOL,
        relation = SchoolPrincipalRelations.APP_MODERATOR
    ) to true,

    // Read own can all
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_OWN,
        relation = SchoolPrincipalRelations.USER
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_OWN,
        relation = SchoolPrincipalRelations.STUDENT
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_OWN,
        relation = SchoolPrincipalRelations.INSTRUCTOR
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_OWN,
        relation = SchoolPrincipalRelations.HEAD
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_OWN,
        relation = SchoolPrincipalRelations.APP_MODERATOR
    ) to true,

    // Read public can all
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_PUBLIC,
        relation = SchoolPrincipalRelations.USER
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_PUBLIC,
        relation = SchoolPrincipalRelations.STUDENT
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_PUBLIC,
        relation = SchoolPrincipalRelations.INSTRUCTOR
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_PUBLIC,
        relation = SchoolPrincipalRelations.HEAD
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = SchoolUserPermissions.READ_SCHOOL_PUBLIC,
        relation = SchoolPrincipalRelations.APP_MODERATOR
    ) to true,

    //Update can only instructor and head of school
    AccessTableConditions(
        operation = CommonOperations.UPDATE,
        permission = SchoolUserPermissions.UPDATE_SCHOOL,
        relation = SchoolPrincipalRelations.INSTRUCTOR
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.UPDATE,
        permission = SchoolUserPermissions.UPDATE_SCHOOL,
        relation = SchoolPrincipalRelations.HEAD
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.UPDATE,
        permission = SchoolUserPermissions.UPDATE_SCHOOL,
        relation = SchoolPrincipalRelations.APP_MODERATOR
    ) to true,

    // Delete
    AccessTableConditions(
        operation = CommonOperations.DELETE,
        permission = SchoolUserPermissions.DELETE_SCHOOL,
        relation = SchoolPrincipalRelations.HEAD
    ) to true,
    AccessTableConditions(
        operation = CommonOperations.DELETE,
        permission = SchoolUserPermissions.DELETE_SCHOOL,
        relation = SchoolPrincipalRelations.APP_MODERATOR
    ) to true

)
