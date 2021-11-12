package ru.kotlin.paraglider.vbaa.app.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.WakeupException
import ru.kotlin.paraglider.vbaa.be.common.context.SchoolContext
import ru.kotlin.paraglider.vbaa.openapi.models.BaseMessage
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class AppKafkaConsumer(private val config: AppKafkaConfig) {
    private val consumer = config.kafkaConsumer
    private val producer = config.kafkaProducer
    private val service = config.service
    private val om = ObjectMapper()
    private val process = AtomicBoolean(true)

    fun run() = runBlocking {
        try {
            consumer.subscribe(listOf(config.kafkaTopicIn))

            while (process.get()) {

                val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(1))
                records.forEach { record ->
                    val context = SchoolContext()
                    try {
                        val request = withContext(Dispatchers.IO) {
                            om.readValue(record.value(), BaseMessage::class.java)
                        }
                        sendResponse(service.handleSchool(context, request))
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Throwable) {
                        sendResponse(service.errorSchool(context, e))
                    }
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: WakeupException) {
            // ignore for shutdown
        } catch (ex: RuntimeException) {
            // exception handling
            withContext(NonCancellable) {
                throw ex
            }
        } finally {
            withContext(NonCancellable) {
                consumer.close()
            }
        }
    }

    private suspend fun sendResponse(response: BaseMessage) {
        val json = withContext(Dispatchers.IO) { om.writeValueAsString(response) }
        val resRecord = ProducerRecord(
            config.kafkaTopicOut,
            UUID.randomUUID().toString(),
            json
        )
        producer.send(resRecord)
    }

    fun stop() {
        process.set(false)
    }

}