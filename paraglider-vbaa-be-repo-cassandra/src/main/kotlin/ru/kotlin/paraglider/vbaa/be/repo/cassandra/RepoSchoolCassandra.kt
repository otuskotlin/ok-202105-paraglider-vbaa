package ru.kotlin.paraglider.vbaa.be.repo.cassandra

import ru.kotlin.paraglider.vbaa.be.repo.common.*

class RepoSchoolCassandra: IRepoSchool {
    override suspend fun create(rq: DbSchoolModelRequest): DbSchoolResponse {
        TODO("Not yet implemented")
    }

    override suspend fun read(rq: DbSchoolIdListRequest): DbSchoolListResponse {
        TODO("Not yet implemented")
    }

    override suspend fun update(rq: DbSchoolModelRequest): DbSchoolResponse {
        TODO("Not yet implemented")
    }

    override suspend fun delete(rq: DbSchoolIdRequest): DbSchoolResponse {
        TODO("Not yet implemented")
    }

    override suspend fun search(rq: DbSchoolFilterRequest): DbSchoolListResponse {
        TODO("Not yet implemented")
    }
}