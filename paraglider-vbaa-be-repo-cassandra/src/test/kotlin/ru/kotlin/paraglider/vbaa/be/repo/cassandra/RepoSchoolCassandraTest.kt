package ru.kotlin.paraglider.vbaa.be.repo.cassandra

import ru.kotlin.paraglider.vbaa.be.common.models.SchoolModel
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
import ru.kotlin.paraglider.vbaa.be.repo.test.*

class RepoSchoolCassandraCreateTest: RepoSchoolCreateTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects)
}
class RepoSchoolCassandraReadTest: RepoSchoolReadTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects)
}
class RepoSchoolCassandraUpdateTest: RepoSchoolUpdateTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects)
}
class RepoSchoolCassandraDeleteTest: RepoSchoolDeleteTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects)
}
class RepoSchoolCassandraSearchTest: RepoSchoolSearchTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects)
}

object TestCompanion {
    fun createRepo(initObjects: List<SchoolModel>): IRepoSchool = RepoSchoolCassandra()
}