package ru.kotlin.paraglider.vbaa.be.repo.test

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolIdListRequest
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolIdRequest
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolModelRequest
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class RepoSchoolDeleteTest {
    abstract val repo: IRepoSchool

    @Test
    fun deleteSuccess() {
        val result = runBlocking { repo.delete(DbSchoolIdRequest(successId)) }

        assertEquals(true, result.isSuccess)
        assertEquals(deleteSuccessStub, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun deleteNotFound() {
        val result = runBlocking { repo.delete(DbSchoolIdRequest(notFoundId)) }

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(CommonErrorModel(field = "id", message = "Not Found").message,
            result.errors.first().message
        )
    }

    companion object: BaseInitSchools() {
        override val initObjects: List<SchoolModel> = listOf(
            createInitTestModel("delete")
        )
        private val deleteSuccessStub = initObjects.first()
        val successId = deleteSuccessStub.id
        val notFoundId = SchoolIdModel(UUID.randomUUID())
    }
}