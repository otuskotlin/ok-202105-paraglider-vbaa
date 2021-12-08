package ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.school

import com.datastax.driver.core.DataType
import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.repo.cassandra.entity.common.PermissionEntity

// Notion
// School and Instructor are separate microservices
// we store only their ids to have front-end to send corresponding request
//DO NOT use inner Model in Entity models
@Entity
data class SchoolCassandraEntity(
    @CqlName(COLUMN_ID)
    @PartitionKey
    val id: String? = null,
    @CqlName(COLUMN_NAME)
    var name: String? = null,
    @CqlName(COLUMN_WELCOME_VIDEO_URL)
    var welcomeVideoUrl: String? = null,
    @CqlName(COLUMN_SHORT_INFO)
    var shortInfo: String? = null,
    @CqlName(COLUMN_ADDRESS)
    var address: String? = null,
    @CqlName(COLUMN_GEOLOCATION)
    var geolocation: String? = null,
    @CqlName(COLUMN_LOCATION_SHORT_INFO)
    var locationShortInfo: String? = null,
    @CqlName(COLUMN_MOBILE_PHONES)
    var mobilePhones: List<String>? = null,
    @CqlName(COLUMN_SOCIAL_MEDIA)
    var socialMedia: List<String>? = null,
    @CqlName(COLUMN_EMAIL)
    var email: String? = null,
    //instructor id
    @CqlName(COLUMN_HEAD_OF_SCHOOL)
    var headOfSchool: String? = null,
    //set of instructor ids
    @CqlName(COLUMN_INSTRUCTORS)
    var instructors: Set<String>? = null,
    //set of service ids
    @CqlName(COLUMN_SERVICES)
    var services: Set<String>? = null,
    @CqlName(COLUMN_STATUS)
    var status: SchoolStatusEntity? = null,
    @CqlName(COLUMN_PERMISSIONS)
    var permissions: MutableSet<PermissionEntity>? = null,
) {
    constructor(schoolModel: SchoolModel) : this(
        id = schoolModel.id.takeIf { it != SchoolIdModel.NONE }?.asString(),
        name = schoolModel.name.takeIf { it.isNotBlank() },
        welcomeVideoUrl = schoolModel.welcomeVideoUrl.takeIf { it.isNotBlank() },
        shortInfo = schoolModel.shortInfo.takeIf { it.isNotBlank() },
        address = schoolModel.location.address.takeIf { it.isNotBlank() },
        geolocation = schoolModel.location.geolocation.takeIf { it.isNotBlank() },
        locationShortInfo = schoolModel.location.shortInfo.takeIf { it.isNotBlank() },
        mobilePhones = schoolModel.contactInfo.mobilePhones.takeIf { it.isNotEmpty() },
        socialMedia = schoolModel.contactInfo.socialMedia.takeIf { it.isNotEmpty() },
        email = schoolModel.contactInfo.email.takeIf { it.isNotBlank() },
        headOfSchool = schoolModel.headOfSchool.takeIf { it != UserIdModel.NONE }?.asString(),
        instructors = schoolModel.instructors.map { it.asString() }.toMutableSet(),
        services = schoolModel.services.map { it.asString() }.toMutableSet(),
        status = schoolModel.status.takeIf{ it != SchoolStatusModel.NONE }?.let { SchoolStatusEntity.valueOf(it.name) },
        permissions = schoolModel.permissions.takeIf { it.isNotEmpty() }?.map { PermissionEntity.valueOf(it.name) }?.toMutableSet()
    )

    fun toModel(): SchoolModel = SchoolModel(
        id = id?.let { SchoolIdModel(it) } ?: SchoolIdModel.NONE,
        name = name ?: "",
        welcomeVideoUrl = welcomeVideoUrl ?: "",
        shortInfo = shortInfo ?: "",
        location = LocationModel(
            address = address ?: "",
            geolocation = geolocation ?: "",
            shortInfo = locationShortInfo ?: ""
        ), contactInfo = ContactInfoModel(
            mobilePhones = mobilePhones.orEmpty().toMutableList(),
            socialMedia = socialMedia.orEmpty().toMutableList(),
            email = email ?: ""
        ),
        headOfSchool = headOfSchool?.let { UserIdModel(it) } ?: UserIdModel.NONE,
        instructors = instructors.orEmpty().map { UserIdModel(it) }.toMutableSet(),
        services = services.orEmpty().map { ServiceIdModel(it) }.toMutableSet(),
        status = status?.let { SchoolStatusModel.valueOf(it.name) } ?: SchoolStatusModel.NONE,
        permissions = permissions.orEmpty().map { PermissionModel.valueOf(it.name) }.toMutableSet()
    )

    companion object {
        const val TABLE_NAME = "school"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_WELCOME_VIDEO_URL = "welcome_video_url"
        const val COLUMN_HEAD_OF_SCHOOL = "head_of_school"
        const val COLUMN_SHORT_INFO = "short_info"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_GEOLOCATION = "geolocation"
        const val COLUMN_LOCATION_SHORT_INFO = "location_short_info"
        const val COLUMN_INSTRUCTORS = "instructors"
        const val COLUMN_MOBILE_PHONES = "mobile_phones"
        const val COLUMN_SOCIAL_MEDIA = "social_media"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_SERVICES = "services"
        const val COLUMN_STATUS = "status"
        const val COLUMN_PERMISSIONS = "permissions"

        //TODO fix with cassandra-migration tool
        fun table(keyspace: String, tableName: String) =
            SchemaBuilder
                .createTable(keyspace, tableName)
                .ifNotExists()
                .withPartitionKey(COLUMN_ID, DataTypes.TEXT)
                .withColumn(COLUMN_NAME, DataTypes.TEXT)
                .withColumn(COLUMN_WELCOME_VIDEO_URL, DataTypes.TEXT)
                .withColumn(COLUMN_HEAD_OF_SCHOOL, DataTypes.TEXT)
                .withColumn(COLUMN_SHORT_INFO, DataTypes.TEXT)
                .withColumn(COLUMN_ADDRESS, DataTypes.TEXT)
                .withColumn(COLUMN_GEOLOCATION, DataTypes.TEXT)
                .withColumn(COLUMN_LOCATION_SHORT_INFO, DataTypes.TEXT)
                .withColumn(COLUMN_INSTRUCTORS, DataTypes.setOf(DataTypes.TEXT))
                .withColumn(COLUMN_MOBILE_PHONES, DataTypes.listOf(DataTypes.TEXT))
                .withColumn(COLUMN_SOCIAL_MEDIA, DataTypes.listOf(DataTypes.TEXT))
                .withColumn(COLUMN_EMAIL, DataTypes.TEXT)
                .withColumn(COLUMN_SERVICES, DataTypes.setOf(DataTypes.TEXT))
                .withColumn(COLUMN_STATUS, DataTypes.TEXT)
                .withColumn(COLUMN_PERMISSIONS, DataTypes.setOf(DataTypes.TEXT))
                .build()

    }
}