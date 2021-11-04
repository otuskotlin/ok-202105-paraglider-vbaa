import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
import ru.kotlin.paraglider.vbaa.be.repo.test.RepoSchoolCreateTest
import ru.kotlin.paraglider.vbaa.be.repo.test.RepoSchoolDeleteTest
import ru.kotlin.paraglider.vbaa.be.repo.test.RepoSchoolReadTest
import ru.kotlin.paraglider.vbaa.be.repo.test.RepoSchoolUpdateTest

class RepoSchoolInMemoryUpdateTest: RepoSchoolUpdateTest() {
    override val repo: IRepoSchool = RepoSchoolInMemory(
        initObjects = initObjects
    )
}