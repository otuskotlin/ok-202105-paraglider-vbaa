package ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.school

import com.datastax.oss.driver.api.mapper.annotations.*
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolFilterRequest
import java.util.concurrent.CompletionStage

@Dao
interface SchoolCassandraDAO {

    @Insert
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    fun create(entity: SchoolCassandraEntity): CompletionStage<Unit>

    @Select
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    fun read(id: String): CompletionStage<SchoolCassandraEntity?>

    @Update(ifExists = true)
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    fun update(dto: SchoolCassandraEntity): CompletionStage<Unit>

    @Delete
    @StatementAttributes(consistencyLevel = "LOCAL_QUORUM")
    fun delete(dto: SchoolCassandraEntity): CompletionStage<Unit>

    @QueryProvider(providerClass = SchoolCassandraSearchProvider::class, entityHelpers = [SchoolCassandraEntity::class])
    fun search(filter: DbSchoolFilterRequest): CompletionStage<Collection<SchoolCassandraEntity>>

}