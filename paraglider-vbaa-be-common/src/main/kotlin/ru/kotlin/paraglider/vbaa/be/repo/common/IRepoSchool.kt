package ru.kotlin.paraglider.vbaa.be.repo.common

interface IRepoSchool: IRepo {
    suspend fun create(rq: DbSchoolModelRequest): DbSchoolResponse
    suspend fun read(rq: DbSchoolIdListRequest): DbSchoolListResponse
    suspend fun update(rq: DbSchoolModelRequest): DbSchoolResponse
    suspend fun delete(rq: DbSchoolIdRequest): DbSchoolResponse
    suspend fun search(rq: DbSchoolFilterRequest): DbSchoolListResponse

    object NONE: IRepoSchool {
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
}