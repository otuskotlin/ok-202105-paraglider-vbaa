package ru.kotlin.paraglider.vbaa.kmp.transport

import kotlinx.serialization.json.Json
import ru.kotlin.paraglider.vbaa.kmp.transport.models.CreateSchoolRequest
import kotlin.test.Test
import kotlin.test.assertContains

class SerializationTest {
    private val json = Json {
        prettyPrint = true
        useAlternativeNames = true
        encodeDefaults = true
    }
    val dto = CreateSchoolRequest(
        requestId = "234"
    )
    @Test
    fun schoolSerializeTest() {
        val serializedString = json.encodeToString(CreateSchoolRequest.serializer(),dto)
        assertContains(serializedString, Regex("requestId\":\\s*\"234"))
    }

}