package ru.kotlin.paraglider.vbaa.be.common.models

data class CommonPrincipalModel(
    val id: UserIdModel = UserIdModel.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<CommonUserGroups> = emptySet()
) {
    companion object {
        val NONE = CommonPrincipalModel()
    }
}
