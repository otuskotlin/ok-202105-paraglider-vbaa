import ru.kotlin.paraglider.vbaa.be.common.models.CommonPrincipalModel
import ru.kotlin.paraglider.vbaa.be.common.models.CommonUserGroups
import ru.kotlin.paraglider.vbaa.be.common.models.UserIdModel
import ru.kotlin.paraglider.vbaa.be.stubs.SchoolStub

fun principalUser(id: UserIdModel = UserIdModel("uuid-user-234"), banned: Boolean = false) = CommonPrincipalModel(
    id = id,
    groups = setOf(
        CommonUserGroups.USER,
        CommonUserGroups.TEST,
        if (banned) CommonUserGroups.BLOCKED_USER else null
    )
        .filterNotNull()
        .toSet()
)

fun principalSchoolHead(id: UserIdModel = SchoolStub.getModel().headOfSchool, banned: Boolean = false) = CommonPrincipalModel(
    id = id,
    groups = setOf(
        CommonUserGroups.SCHOOL_HEAD,
        CommonUserGroups.TEST,
        if (banned) CommonUserGroups.BLOCKED_USER else null
    )
        .filterNotNull()
        .toSet()
)
