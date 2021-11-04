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

abstract class RepoSchoolReadTest {
    abstract val repo: IRepoSchool

    @Test
    fun readSuccess() {
        val result = runBlocking { repo.read(DbSchoolIdListRequest(listOf(successId))) }

        assertEquals(true, result.isSuccess)
        assertEquals(readSuccessStub, result.result?.first())
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() {
        val result = runBlocking { repo.read(DbSchoolIdListRequest(listOf(notFoundId))) }

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(
            CommonErrorModel(field = "idList", message = "Not Found").message,
            result.errors.first().message
        )
    }

    companion object: BaseInitSchools() {
        override val initObjects: List<SchoolModel> = listOf(
            createInitTestModel("read")
        )
        private val readSuccessStub = initObjects.first()

        val successId = readSuccessStub.id
        val notFoundId = SchoolIdModel(UUID.randomUUID())

    }
}