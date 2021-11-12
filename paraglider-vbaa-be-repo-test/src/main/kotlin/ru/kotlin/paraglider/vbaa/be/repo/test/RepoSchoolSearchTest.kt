package ru.kotlin.paraglider.vbaa.be.repo.test

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.repo.common.*
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class RepoSchoolSearchTest {
    abstract val repo: IRepoSchool

    @Test
    fun searchName() {
        val result = runBlocking { repo.search(DbSchoolFilterRequest(searchStr = "My Sky")) }
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[1])
        assertEquals(expected.sortedBy { it.id.asString() }, result.result.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchAddress() {
        val result = runBlocking { repo.search(DbSchoolFilterRequest(location = LocationModel(address = "Podolsk"))) }
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[2], initObjects[3])
        assertEquals(expected.sortedBy { it.id.asString() }, result.result.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun searchEmail() {
        val result = runBlocking { repo.search(DbSchoolFilterRequest(contactInfo = ContactInfoModel(email = "paragliding@gmail.com"))) }
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[4])
        assertEquals(expected.sortedBy { it.id.asString() }, result.result.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitSchools() {

        override val initObjects: List<SchoolModel> = listOf(
            createInitTestModel("school1"),
            createInitTestModel("school2", name = "My Sky"),
            createInitTestModel("school3", address = "Podolsk"),
            createInitTestModel("school4", name = "Infinity", address = "Podolsk"),
            createInitTestModel("school5", email = "paragliding@gmail.com"),
        )
    }
}