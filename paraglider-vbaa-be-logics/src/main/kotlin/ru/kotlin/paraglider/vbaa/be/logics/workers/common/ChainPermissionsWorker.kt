package ru.kotlin.paraglider.vbaa.be.logics.workers.common

import core.ICorChainDsl
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.AbstractContext
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.models.CommonUserGroups
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolUserPermissions

fun ICorChainDsl<out AbstractContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on {
        status == CorStatus.RUNNING
    }

    handle {
        val permAdd: Set<SchoolUserPermissions> = principal.groups.map {
            when(it) {
                CommonUserGroups.USER -> setOf(
                    SchoolUserPermissions.READ_SCHOOL_PUBLIC,
                )
                CommonUserGroups.STUDENT -> setOf(
                    SchoolUserPermissions.READ_SCHOOL_PUBLIC,
                    SchoolUserPermissions.READ_SCHOOL_OWN,
                )
                CommonUserGroups.PILOT -> setOf()
                CommonUserGroups.SCHOOL_HEAD -> setOf(
                    SchoolUserPermissions.READ_SCHOOL_PUBLIC,
                    SchoolUserPermissions.READ_SCHOOL_OWN,
                    SchoolUserPermissions.CREATE_SCHOOL,
                    SchoolUserPermissions.UPDATE_SCHOOL,
                    SchoolUserPermissions.DELETE_SCHOOL,
                )
                CommonUserGroups.INSTRUCTOR -> setOf(
                    SchoolUserPermissions.READ_SCHOOL_PUBLIC,
                    SchoolUserPermissions.READ_SCHOOL_OWN,
                    SchoolUserPermissions.UPDATE_SCHOOL,
                )
                CommonUserGroups.SCHOOL_STUFF -> setOf()
                CommonUserGroups.APP_MODERATOR -> setOf(
                    SchoolUserPermissions.READ_SCHOOL_PUBLIC,
                    SchoolUserPermissions.READ_SCHOOL_OWN,
                    SchoolUserPermissions.CREATE_SCHOOL,
                    SchoolUserPermissions.UPDATE_SCHOOL,
                    SchoolUserPermissions.DELETE_SCHOOL,
                )
                CommonUserGroups.TEST -> setOf(
                    SchoolUserPermissions.READ_SCHOOL_PUBLIC,
                )
                CommonUserGroups.BLOCKED_USER -> setOf(
                    SchoolUserPermissions.READ_SCHOOL_PUBLIC,
                )
            }
        }.flatten().toSet()
        val permDel: Set<SchoolUserPermissions> = principal.groups.map {
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
                    SchoolUserPermissions.READ_SCHOOL_PUBLIC,
                )
            }
        }.flatten().toSet()
        chainPermissions.addAll(permAdd)
        chainPermissions.removeAll(permDel)
        println("PRINCIPAL: $principal")
        println("PERMISSIONS: $chainPermissions")
    }
}
