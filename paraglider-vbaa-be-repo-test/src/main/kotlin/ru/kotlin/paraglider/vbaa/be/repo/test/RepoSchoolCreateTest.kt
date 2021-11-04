package ru.kotlin.paraglider.vbaa.be.repo.test

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.repo.common.DbSchoolModelRequest
import ru.kotlin.paraglider.vbaa.be.repo.common.IRepoSchool
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class RepoSchoolCreateTest {
    abstract val repo: IRepoSchool

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.create(DbSchoolModelRequest(createObj)) }
        val expected = createObj.copy(id = result.result?.id ?: SchoolIdModel.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected, result.result)
        assertNotEquals(SchoolIdModel.NONE, result.result?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitSchools() {
        private val createObj = SchoolModel(
            id = SchoolIdModel(UUID.randomUUID()),
            name = "create object",
            welcomeVideoUrl = "create object welcomeVideoUrl",
            shortInfo = "create object shortInfo",
            location = LocationModel(
                address = "create object address",
                geolocation = "create object geolocation",
                shortInfo = "create object shortInfo"
            ),
            contactInfo = ContactInfoModel(
                mobilePhones = listOf("create object mobilePhone 1"),
                socialMedia = listOf("create object socialMedia 1"),
                email = "create object email"
            ),
            serviceBasicInfo = listOf("create object service 1"),
            status = SchoolStatusModel.ACTIVE
        )
        override val initObjects: List<SchoolModel> = emptyList()
    }
}