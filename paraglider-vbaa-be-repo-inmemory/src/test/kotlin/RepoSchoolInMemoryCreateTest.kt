import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
import ru.kotlin.paraglider.vbaa.be.repo.test.RepoSchoolCreateTest

class RepoSchoolInMemoryCreateTest: RepoSchoolCreateTest() {
    override val repo: IRepoSchool = RepoSchoolInMemory(
        initObjects = initObjects
    )
}