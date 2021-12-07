package ru.kotlin.paraglider.vbaa.be.repo.test

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolModelRequest
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class RepoSchoolUpdateTest {
    abstract val repo: IRepoSchool

    @Test
    fun updateSuccess() {
        val result = runBlocking { repo.update(DbSchoolModelRequest(updateObj)) }
        assertEquals(true, result.isSuccess)
        assertEquals(updateObj, result.result)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() {
        val result = runBlocking { repo.update(DbSchoolModelRequest(updateObjNotFound)) }
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.result)
        assertEquals(
            CommonErrorModel(field = "id", message = "Not Found").message,
            result.errors.first().message
        )
    }

    companion object: BaseInitSchools() {
        override val initObjects: List<SchoolModel> = listOf(
            createInitTestModel("update")
        )
        private val updateId = initObjects.first().id
        private val updateIdNotFound = SchoolIdModel(UUID.randomUUID())

        private val updateObj = SchoolModel(
            id = updateId,
            name = "update object",
            welcomeVideoUrl = "update object welcomeVideoUrl",
            shortInfo = "update object shortInfo",
            location = LocationModel(
                address = "update object address",
                geolocation = "update object geolocation",
                shortInfo = "update object shortInfo"
            ),
            contactInfo = ContactInfoModel(
                mobilePhones = listOf("update object mobilePhone 1"),
                socialMedia = listOf("update object socialMedia 1"),
                email = "update object email"
            ),
            headOfSchool = UserIdModel("uuid-123"),
            instructors = emptySet(),
            services = emptySet(),
            status = SchoolStatusModel.ACTIVE
        )

        private val updateObjNotFound = SchoolModel(
            id = updateIdNotFound,
            name = "update object not found",
            welcomeVideoUrl = "update object not found welcomeVideoUrl",
            shortInfo = "update object not found shortInfo",
            location = LocationModel(
                address = "update object not found address",
                geolocation = "update object not found geolocation",
                shortInfo = "update object not found shortInfo"
            ),
            contactInfo = ContactInfoModel(
                mobilePhones = listOf("update object not found mobilePhone 1"),
                socialMedia = listOf("update object not found socialMedia 1"),
                email = "update object not found email"
            ),
            headOfSchool = UserIdModel("uuid-123"),
            instructors = emptySet(),
            services = emptySet(),
            status = SchoolStatusModel.ACTIVE
        )
    }
}