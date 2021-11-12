package ru.kotlin.paraglider.vbaa.be.repo.cassandra

import kotlinx.coroutines.future.await
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory
import ru.kotlin.paraglider.vbaa.be.common.models.CommonErrorModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolIdModel
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolModel
import ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.school.SchoolCassandraDAO
import ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.school.SchoolCassandraEntity
import ru.kotlin.paraglider.vbaa.be.repo.common.*
import java.util.*

class RepoSchoolCassandra(
    private val dao: SchoolCassandraDAO,
    private val timeoutMillis: Long = 30_000
): IRepoSchool {
    private val log = LoggerFactory.getLogger(javaClass)

    override suspend fun create(rq: DbSchoolModelRequest): DbSchoolResponse {
        val new = rq.school.copy(id = SchoolIdModel(UUID.randomUUID().toString()))
        return try {
            withTimeout(timeoutMillis) { dao.create(SchoolCassandraEntity(new)).await() }
            DbSchoolResponse(new)
        }
        catch (e: Exception) {
            log.error("Failed to create", e)
            DbSchoolResponse(CommonErrorModel(e))
        }
    }

    override suspend fun read(rq: DbSchoolIdListRequest): DbSchoolListResponse {
        return if (rq.idList.isEmpty())
            DbSchoolListResponse(CommonErrorModel(field = "idList", message = "Id List is empty"))
        else try {
            val found = withTimeout(timeoutMillis) {
                val set: MutableSet<SchoolCassandraEntity> = mutableSetOf()
                rq.idList.map { launch {
                    dao.read(it.asString()).await()?.let { saved -> set.add(saved) }
                }}.joinAll()
                set
            }
            if (found.isNotEmpty()) DbSchoolListResponse(found.map { it.toModel() })
            else DbSchoolListResponse(CommonErrorModel(field = "ids", message = "Not Found"))
        } catch (e: Exception) {
            log.error("Failed to read", e)
            DbSchoolListResponse(CommonErrorModel(e))
        }
    }

    override suspend fun update(rq: DbSchoolModelRequest): DbSchoolResponse {
        return if (rq.school.id == SchoolIdModel.NONE)
            DbSchoolResponse(CommonErrorModel(field = "school.id", message = "Invalid value"))
        else try {
            val updated = withTimeout(timeoutMillis) {
                dao.update(SchoolCassandraEntity(rq.school)).await()
                dao.read(rq.school.id.asString()).await()
            }
            if (updated != null) DbSchoolResponse(updated.toModel())
            else DbSchoolResponse(CommonErrorModel(field = "id", message = "Not Found"))
        }
        catch (e: Exception) {
            log.error("Failed to read", e)
            DbSchoolResponse(CommonErrorModel(e))
        }
    }

    override suspend fun delete(rq: DbSchoolIdRequest): DbSchoolResponse {
        return if (rq.id == SchoolIdModel.NONE)
            DbSchoolResponse(CommonErrorModel(field = "id", message = "Id is empty"))
        else try {
            val deleted = withTimeout(timeoutMillis) {
                dao.read(rq.id.asString()).await()?.also {
                    dao.delete(it).await()
                }

            }
            if (deleted != null) DbSchoolResponse(deleted.toModel())
            else DbSchoolResponse(CommonErrorModel(field = "id", message = "Not Found"))
        } catch (e: Exception) {
            log.error("Failed to delete", e)
            DbSchoolResponse(CommonErrorModel(e))
        }
    }

    override suspend fun search(rq: DbSchoolFilterRequest): DbSchoolListResponse {
        return try {
            val found = dao.search(rq).await().map { it.toModel() }
            DbSchoolListResponse(found, true)
        }
        catch (e: Exception) {
            DbSchoolListResponse(CommonErrorModel(e))
        }
    }
}