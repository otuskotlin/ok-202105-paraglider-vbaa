import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import models.SchoolRow
import org.ehcache.Cache
import org.ehcache.CacheManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ExpiryPolicyBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import ru.kotlin.paraglider.vbaa.be.common.models.*
import ru.kotlin.paraglider.vbaa.be.repo.common.*
import java.time.Duration
import java.util.*

class RepoSchoolInMemory(
    private val initObjects: List<SchoolModel> = listOf(),
    private val ttl: Duration = Duration.ofMinutes(1)
): IRepoSchool {

    private val cache: Cache<String, SchoolRow> = let {
        val cacheManager: CacheManager = CacheManagerBuilder
            .newCacheManagerBuilder()
            .build(true)

        cacheManager.createCache(
            "paraglider-school-cache",
            CacheConfigurationBuilder
                .newCacheConfigurationBuilder(
                    String::class.java,
                    SchoolRow::class.java,
                    ResourcePoolsBuilder.heap(100)
                )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(ttl))
                .build()
        )
    }

    init {
        runBlocking { initObjects.forEach {
            save(it)
        } }
    }

    private suspend fun save(item: SchoolModel): DbSchoolResponse {
        val row = SchoolRow(item)
        if (row.id == null) {
            return DbSchoolResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Id must not be null or blank"
                    )
                )
            )
        }
        cache.put(row.id, row)
        return DbSchoolResponse(
            result = row.toInternal(),
            isSuccess = true
        )
    }



    override suspend fun create(rq: DbSchoolModelRequest): DbSchoolResponse =
        save(rq.school.copy(id = SchoolIdModel(UUID.randomUUID().toString())))

    override suspend fun read(rq: DbSchoolIdListRequest): DbSchoolListResponse =
        cache.getAll(rq.idList.map { it.asString() }.toSet())?.let {
            val result = it.values.mapNotNull{
                it?.let { it.toInternal() } ?:
                return@let DbSchoolListResponse(
                    result = emptyList(),
                    isSuccess = false,
                    errors = listOf(
                        CommonErrorModel(
                            field = "id",
                            message = "Not Found"
                        )
                    )
                )
            }.toList()
            return DbSchoolListResponse (
                result = result,
                isSuccess = true
            )
        } ?: DbSchoolListResponse(
            result = emptyList(),
            isSuccess = false,
            errors = listOf(
                CommonErrorModel(
                    field = "idList",
                    message = "Not Found"
                )
            )
        )

    override suspend fun update(rq: DbSchoolModelRequest): DbSchoolResponse {
        val key = rq.school.id.takeIf { it != SchoolIdModel.NONE }?.asString()
            ?: return DbSchoolResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(field = "id", message = "Id must not be null or blank")
                )
            )

        return if (cache.containsKey(key)) {
            save(rq.school)
            DbSchoolResponse(
                result = rq.school,
                isSuccess = true
            )
        }
        else {
            DbSchoolResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(
                        field = "id",
                        message = "Not Found"
                    )
                )
            )
        }

    }

    override suspend fun delete(rq: DbSchoolIdRequest): DbSchoolResponse {
        val key = rq.id.takeIf { it != SchoolIdModel.NONE }?.asString()
            ?: return DbSchoolResponse(
                result = null,
                isSuccess = false,
                errors = listOf(
                    CommonErrorModel(field = "id", message = "Id must not be null or blank")
                )
            )
        val row = cache.get(key) ?: return DbSchoolResponse(
            result = null,
            isSuccess = false,
            errors = listOf(
                CommonErrorModel(field = "id", message = "Not Found")
            )
        )
        cache.remove(key)
        return DbSchoolResponse(
            result = row.toInternal(),
            isSuccess = true,
        )
    }

    override suspend fun search(rq: DbSchoolFilterRequest): DbSchoolListResponse {
        val results = cache.asFlow()
            .filter {
                if (rq.searchStr.isBlank()) return@filter true
                rq.searchStr == it.value.name
            }
            .filter {
                if (rq.location.address.isBlank()) return@filter true
                rq.location.address == it.value.location?.address
            }
            .filter {
                if (rq.location.geolocation.isBlank()) return@filter true
                rq.location.geolocation == it.value.location?.geolocation
            }
            .filter {
                if (rq.location.shortInfo.isBlank()) return@filter true
                rq.location.shortInfo == it.value.location?.shortInfo
            }
            .filter {
                if (rq.contactInfo.email.isBlank()) return@filter true
                rq.contactInfo.email == it.value.contactInfo?.email
            }
            .filter {
                if (rq.contactInfo.mobilePhones.isEmpty()) return@filter true
                it.value.contactInfo?.mobilePhones?.containsAll(rq.contactInfo.mobilePhones)
                    ?: return@filter true
            }
            .filter {
                if (rq.contactInfo.socialMedia.isEmpty()) return@filter true
                it.value.contactInfo?.socialMedia?.containsAll(rq.contactInfo.socialMedia)
                    ?: return@filter true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbSchoolListResponse(
            result = results,
            isSuccess = true
        )
    }
}