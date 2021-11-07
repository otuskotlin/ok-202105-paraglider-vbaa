package ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.school

import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolFilterRequest
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer

class SchoolCassandraSearchProvider(
    private val context: MapperContext,
    private val entityHelper: EntityHelper<SchoolCassandraEntity>
) {
    fun search(filter: DbSchoolFilterRequest): CompletionStage<Collection<SchoolCassandraEntity>> {
        var select = entityHelper.selectStart().allowFiltering()

        if (filter.searchStr.isNotBlank()) {
            select = select
                .whereColumn(SchoolCassandraEntity.COLUMN_NAME)
                .isEqualTo(QueryBuilder.literal(filter.searchStr))
        }
        if (filter.location.address.isNotBlank()) {
            select = select
                .whereColumn(SchoolCassandraEntity.COLUMN_ADDRESS)
                .isEqualTo(QueryBuilder.literal(filter.location.address))
        }
        if (filter.contactInfo.email.isNotBlank()) {
            select = select
                .whereColumn(SchoolCassandraEntity.COLUMN_EMAIL)
                .isEqualTo(QueryBuilder.literal(filter.contactInfo.email))
        }

        val asyncFetcher = AsynchFetcher()

        context.session
            .executeAsync(select.build())
            .whenComplete(asyncFetcher)

        return asyncFetcher.stage
    }

    inner class AsynchFetcher : BiConsumer<AsyncResultSet?, Throwable?> {
        private val buffer = mutableListOf<SchoolCassandraEntity>()
        private val future = CompletableFuture<Collection<SchoolCassandraEntity>>()
        val stage: CompletionStage<Collection<SchoolCassandraEntity>> = future

        override fun accept(resultSet: AsyncResultSet?, t: Throwable?) {
            when {
                t != null -> future.completeExceptionally(t)
                resultSet == null -> future.completeExceptionally(IllegalStateException("ResultSet should not be null"))
                else -> {
                    buffer.addAll(resultSet.currentPage().map { entityHelper.get(it) })
                    if (resultSet.hasMorePages())
                        resultSet.fetchNextPage().whenComplete(this)
                    else
                        future.complete(buffer)
                }
            }
        }
    }

}