package ru.kotlin.paraglider.vbaa.be.logics.workers.common

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.models.CommonUserGroups
import ru.kotlin.paraglider.vbaa.be.common.models.CommonUserPermissions

fun ICorChainDsl<out AbstractContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on {
        status == CorStatus.RUNNING
    }

    handle {
        val permAdd: Set<CommonUserPermissions> = principal.groups.map {
            when(it) {
                CommonUserGroups.USER -> setOf(
                    CommonUserPermissions.READ_SCHOOL_PUBLIC,
                )
                CommonUserGroups.STUDENT -> setOf()
                CommonUserGroups.PILOT -> setOf()
                CommonUserGroups.SCHOOL_HEAD -> setOf(
                    CommonUserPermissions.READ_SCHOOL_PUBLIC,
                    CommonUserPermissions.READ_SCHOOL_OWN,
                    CommonUserPermissions.CREATE_SCHOOL,
                    CommonUserPermissions.UPDATE_SCHOOL_OWN,
                    CommonUserPermissions.DELETE_SCHOOL_OWN,
                )
                CommonUserGroups.INSTRUCTOR -> setOf()
                CommonUserGroups.SCHOOL_STUFF -> setOf(
                    CommonUserPermissions.READ_SCHOOL_PUBLIC,
                )
                CommonUserGroups.APP_MODERATOR -> setOf(
                    CommonUserPermissions.ROOT
                )
                CommonUserGroups.TEST -> setOf()
                CommonUserGroups.BLOCKED_USER -> setOf()
            }
        }.flatten().toSet()
        val permDel: Set<CommonUserPermissions> = principal.groups.map {
            when(it) {
                CommonUserGroups.USER -> setOf()
                CommonUserGroups.STUDENT -> setOf()
                CommonUserGroups.PILOT -> setOf()
                CommonUserGroups.SCHOOL_HEAD -> setOf()
                CommonUserGroups.INSTRUCTOR -> setOf()
                CommonUserGroups.SCHOOL_STUFF -> setOf()
                CommonUserGroups.APP_MODERATOR -> setOf()
                CommonUserGroups.TEST -> setOf()
                CommonUserGroups.BLOCKED_USER -> setOf(
                    CommonUserPermissions.READ_SCHOOL_PUBLIC,
                )
            }
        }.flatten().toSet()
        chainPermissions.addAll(permAdd)
        chainPermissions.removeAll(permDel)
        println("PRINCIPAL: $principal")
        println("PERMISSIONS: $chainPermissions")
    }
}
