import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
import ru.kotlin.paraglider.vbaa.be.repo.test.RepoSchoolCreateTest
import ru.kotlin.paraglider.vbaa.be.repo.test.RepoSchoolDeleteTest

class RepoSchoolInMemoryDeleteTest: RepoSchoolDeleteTest() {
    override val repo: IRepoSchool = RepoSchoolInMemory(
        initObjects = initObjects
    )
}