package ru.kotlin.paraglider.vbaa.be.logics.workers.common

import core.ICorChainDsl
import handlers.chain
import handlers.worker
import ru.kotlin.paraglider.vbaa.be.common.context.CorStatus
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.be.common.models.CommonUserGroups
import ru.kotlin.paraglider.vbaa.be.common.models.PermissionModel

fun ICorChainDsl<SchoolContext>.frontPermissions(title: String) = chain {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { status == CorStatus.RUNNING }

    worker {
        this.title = "Разрешения для всех пользователей"
        description = this.title
        handle {
            val permAdd: Set<PermissionModel> = principal.groups.map {
                when (it) {
                    CommonUserGroups.USER -> setOf(
                        PermissionModel.READ
                    )
                    CommonUserGroups.STUDENT -> setOf()
                    CommonUserGroups.PILOT -> setOf()
                    CommonUserGroups.SCHOOL_HEAD -> setOf()
                    CommonUserGroups.INSTRUCTOR -> setOf()
                    CommonUserGroups.SCHOOL_STUFF -> setOf()
                    CommonUserGroups.APP_MODERATOR -> setOf()
                    CommonUserGroups.TEST -> setOf()
                    CommonUserGroups.BLOCKED_USER -> setOf()
                }
            }.flatten().toSet()
            val permDel: Set<PermissionModel> = principal.groups.map {
                when (it) {
                    CommonUserGroups.USER -> setOf()
                    CommonUserGroups.STUDENT -> setOf()
                    CommonUserGroups.PILOT -> setOf()
                    CommonUserGroups.SCHOOL_HEAD -> setOf()
                    CommonUserGroups.INSTRUCTOR -> setOf()
                    CommonUserGroups.SCHOOL_STUFF -> setOf()
                    CommonUserGroups.APP_MODERATOR -> setOf()
                    CommonUserGroups.TEST -> setOf()
                    CommonUserGroups.BLOCKED_USER -> setOf(
                        PermissionModel.READ
                    )
                }
            }.flatten().toSet()
            responseSchool.permissions.addAll(permAdd)
            responseSchool.permissions.removeAll(permDel)
        }
    }

    worker {
        this.title = "Разрешения для руководителя школы"
        description = this.title
        on{ responseSchool.headOfSchool == principal.id
                || responseSchool.instructors.contains(principal.id) }
        handle {
            val permAdd: Set<PermissionModel> = principal.groups.map {
                when (it) {
                    CommonUserGroups.USER -> setOf()
                    CommonUserGroups.STUDENT -> setOf()
                    CommonUserGroups.PILOT -> setOf()
                    CommonUserGroups.SCHOOL_HEAD -> setOf(
                        PermissionModel.READ,
                        PermissionModel.CREATE,
                        PermissionModel.UPDATE,
                        PermissionModel.DELETE,
                    )
                    CommonUserGroups.INSTRUCTOR -> setOf()
                    CommonUserGroups.SCHOOL_STUFF -> setOf()
                    CommonUserGroups.APP_MODERATOR -> setOf()
                    CommonUserGroups.TEST -> setOf()
                    CommonUserGroups.BLOCKED_USER -> setOf()
                }
            }.flatten().toSet()
            val permDel: Set<PermissionModel> = principal.groups.map {
                when (it) {
                    CommonUserGroups.USER -> setOf()
                    CommonUserGroups.STUDENT -> setOf()
                    CommonUserGroups.PILOT -> setOf()
                    CommonUserGroups.SCHOOL_HEAD -> setOf()
                    CommonUserGroups.INSTRUCTOR -> setOf()
                    CommonUserGroups.SCHOOL_STUFF -> setOf()
                    CommonUserGroups.APP_MODERATOR -> setOf()
                    CommonUserGroups.TEST -> setOf()
                    CommonUserGroups.BLOCKED_USER -> setOf(
                        PermissionModel.READ,
                        PermissionModel.CREATE,
                        PermissionModel.UPDATE,
                        PermissionModel.DELETE,
                    )
                }
            }.flatten().toSet()
            responseSchool.permissions.addAll(permAdd)
            responseSchool.permissions.removeAll(permDel)
        }
    }
    worker {
        this.title = "Разрешения для инструкторов и персонала школы"
        description = this.title
        on{ responseSchool.instructors.contains(principal.id) }
        handle {
            val permAdd: Set<PermissionModel> = principal.groups.map {
                when (it) {
                    CommonUserGroups.USER -> setOf()
                    CommonUserGroups.STUDENT -> setOf()
                    CommonUserGroups.PILOT -> setOf()
                    CommonUserGroups.SCHOOL_HEAD -> setOf()
                    CommonUserGroups.INSTRUCTOR -> setOf(
                        PermissionModel.READ,
                        PermissionModel.UPDATE,
                    )
                    CommonUserGroups.SCHOOL_STUFF -> setOf(
                        PermissionModel.READ,
                        PermissionModel.UPDATE,
                    )
                    CommonUserGroups.APP_MODERATOR -> setOf()
                    CommonUserGroups.TEST -> setOf()
                    CommonUserGroups.BLOCKED_USER -> setOf()
                }
            }.flatten().toSet()
            val permDel: Set<PermissionModel> = principal.groups.map {
                when (it) {
                    CommonUserGroups.USER -> setOf()
                    CommonUserGroups.STUDENT -> setOf()
                    CommonUserGroups.PILOT -> setOf()
                    CommonUserGroups.INSTRUCTOR -> setOf(
                        PermissionModel.CREATE,
                        PermissionModel.DELETE,
                    )
                    CommonUserGroups.SCHOOL_STUFF -> setOf(
                        PermissionModel.CREATE,
                        PermissionModel.DELETE,
                    )
                    CommonUserGroups.SCHOOL_HEAD -> setOf()
                    CommonUserGroups.APP_MODERATOR -> setOf()
                    CommonUserGroups.TEST -> setOf()
                    CommonUserGroups.BLOCKED_USER -> setOf(
                        PermissionModel.READ,
                        PermissionModel.CREATE,
                        PermissionModel.UPDATE,
                        PermissionModel.DELETE,
                    )
                }
            }.flatten().toSet()
            responseSchool.permissions.addAll(permAdd)
            responseSchool.permissions.removeAll(permDel)
        }
    }
    worker {
        this.title = "Разрешения для администратора приложения"
        description = this.title
    }
}
