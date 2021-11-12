package ru.kotlin.paraglider.vbaa.be.repo.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.core.type.codec.extras.enums.EnumNameCodec
import com.datastax.oss.driver.internal.core.type.codec.registry.DefaultCodecRegistry
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures
import org.testcontainers.containers.CassandraContainer
import ru.kotlin.paraglider.vbaa.be.common.models.SchoolModel
import ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.common.PermissionEntity
import ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.school.CassandraMapper
import ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.school.SchoolCassandraEntity
import ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.school.SchoolStatusEntity
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
import ru.kotlin.paraglider.vbaa.be.repo.test.*
import java.net.InetSocketAddress

class RepoSchoolCassandraCreateTest: RepoSchoolCreateTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects, "ks_create")
}
class RepoSchoolCassandraReadTest: RepoSchoolReadTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects, "ks_read")
}
class RepoSchoolCassandraUpdateTest: RepoSchoolUpdateTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects, "ks_update")
}
class RepoSchoolCassandraDeleteTest: RepoSchoolDeleteTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects, "ks_delete")
}
class RepoSchoolCassandraSearchTest: RepoSchoolSearchTest() {
    override val repo: IRepoSchool = TestCompanion.createRepo(initObjects, "ks_search")
}

class TestCassandraContainer: CassandraContainer<TestCassandraContainer>("cassandra:3.11.2")

object TestCompanion {
    val container by lazy { TestCassandraContainer().apply { start() } }

    val codecRegistry by lazy {
        DefaultCodecRegistry("default").apply {
            register(EnumNameCodec(SchoolStatusEntity::class.java))
            register(EnumNameCodec(PermissionEntity::class.java))
        }
    }

    val session by lazy {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress(container.host, container.getMappedPort(CassandraContainer.CQL_PORT)))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(container.username, container.password)
            .withCodecRegistry(codecRegistry)
            .build()
    }

    val mapper by lazy { CassandraMapper.builder(session).build() }

    fun createSchema(keyspace: String) {
        session.execute(
            SchemaBuilder
                .createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(1)
                .build()
        )
        session.execute(SchoolCassandraEntity.table(keyspace, SchoolCassandraEntity.TABLE_NAME))
//        session.execute(SchoolCassandraEntity.titleIndex(keyspace, SchoolCassandraEntity.TABLE_NAME))
    }

    fun createRepo(initObjects: List<SchoolModel>, keyspace: String): RepoSchoolCassandra {
        createSchema(keyspace)
        val dao = mapper.schoolCassandraDao(keyspace, SchoolCassandraEntity.TABLE_NAME)
        CompletableFutures
            .allDone(initObjects.map { dao.create(SchoolCassandraEntity(it)) })
            .toCompletableFuture()
            .get()

        return RepoSchoolCassandra(dao)
    }
}