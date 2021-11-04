package ru.kotlin.paraglider.vbaa.be.repo.cassandra

import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
import ru.kotlin.paraglider.vbaa.be.repo.test.*

class RepoSchoolCassandraCreateTest: RepoSchoolCreateTest() {
    override val repo: IRepoSchool =TestCompanion.createRepo()
}
class RepoSchoolCassandraReadTest: RepoSchoolReadTest() {
    override val repo: IRepoSchool =TestCompanion.createRepo()
}
class RepoSchoolCassandraUpdateTest: RepoSchoolUpdateTest() {
    override val repo: IRepoSchool =TestCompanion.createRepo()
}
class RepoSchoolCassandraDeleteTest: RepoSchoolDeleteTest() {
    override val repo: IRepoSchool =TestCompanion.createRepo()
}
class RepoSchoolCassandraSearchTest: RepoSchoolSearchTest() {
    override val repo: IRepoSchool =TestCompanion.createRepo()
}

object TestCompanion {
    fun createRepo(): IRepoSchool = RepoSchoolCassandra()
}