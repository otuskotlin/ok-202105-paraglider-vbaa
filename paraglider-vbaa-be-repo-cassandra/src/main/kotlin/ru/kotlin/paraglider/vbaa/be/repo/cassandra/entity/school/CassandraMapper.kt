package ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.school

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace
import com.datastax.oss.driver.api.mapper.annotations.DaoTable
import com.datastax.oss.driver.api.mapper.annotations.Mapper

@Mapper
interface CassandraMapper {
    @DaoFactory
    fun schoolCassandraDao(
        @DaoKeyspace keyspace: String,
        @DaoTable table: String
    ): SchoolCassandraDAO

    companion object {
        fun builder(session: CqlSession) = CassandraMapperBuilder(session)
    }
}