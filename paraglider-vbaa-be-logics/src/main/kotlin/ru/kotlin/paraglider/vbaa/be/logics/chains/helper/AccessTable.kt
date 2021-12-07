package ru.kotlin.paraglider.vbaa.be.logics.chains.helper

import ru.kotlin.paraglider.vbaa.be.common.context.CommonOperations
import ru.kotlin.paraglider.vbaa.be.common.models.CommonPrincipalRelations
import ru.kotlin.paraglider.vbaa.be.common.models.CommonUserPermissions

data class AccessTableConditions(
    val operation: CommonOperations,
    val permission: CommonUserPermissions,
    val relation: CommonPrincipalRelations
)

val accessTable = mapOf(
    // Create
    AccessTableConditions(
        operation = CommonOperations.CREATE,
        permission = CommonUserPermissions.CREATE_SCHOOL,
        relation = CommonPrincipalRelations.NONE
    ) to true,

    // Read
    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = CommonUserPermissions.READ_SCHOOL_OWN,
        relation = CommonPrincipalRelations.OWN
    ) to true,

    AccessTableConditions(
        operation = CommonOperations.READ,
        permission = CommonUserPermissions.READ_SCHOOL_PUBLIC,
        relation = CommonPrincipalRelations.PUBLIC
    ) to true,

    // Update
    AccessTableConditions(
        operation = CommonOperations.UPDATE,
        permission = CommonUserPermissions.UPDATE_SCHOOL_OWN,
        relation = CommonPrincipalRelations.OWN
    ) to true,

    // Delete
    AccessTableConditions(
        operation = CommonOperations.DELETE,
        permission = CommonUserPermissions.DELETE_SCHOOL_OWN,
        relation = CommonPrincipalRelations.OWN
    ) to true

)
