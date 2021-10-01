import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.kotlin.paraglider.vbaa.app.kafka.AppKafkaConfig
import ru.kotlin.paraglider.vbaa.app.kafka.AppKafkaConsumer
import ru.kotlin.paraglider.vbaa.openapi.models.BaseDebugRequest
import ru.kotlin.paraglider.vbaa.openapi.models.BaseMessage
import ru.kotlin.paraglider.vbaa.openapi.models.GetSchoolRequest
import ru.kotlin.paraglider.vbaa.openapi.models.GetSchoolResponse
import java.util.*
import kotlin.test.assertEquals

class KafkaControllerTest {

    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig(
            kafkaConsumer = consumer,
            kafkaProducer = producer
        )
        val app = AppKafkaConsumer(config)

        consumer.schedulePollTask{
            consumer.rebalance(Collections.singletonList(TopicPartition(config.kafkaTopicIn, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    config.kafkaTopicIn,
                    PARTITION,
                    0L,
                    "devs-1-dptmnt",
                    GetSchoolRequest(
                        requestId = "getSchool123",
                        schoolIdList = setOf("123"),
                        debug = BaseDebugRequest(
                            mode = BaseDebugRequest.Mode.STUB,
                            stubCase = BaseDebugRequest.StubCase.SUCCESS
                        )
                    ).toJson()
                )
            )
            app.stop()
        }

        //configuring topic partition start offset
        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(config.kafkaTopicIn, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        //start app
        app.run()


        val message: ProducerRecord<String, String> = producer.history().first()
        val result = message.value().fromJson<GetSchoolResponse>()
        assertEquals("getSchool123", result.requestId)
        assertEquals("MyNebo", result.readSchoolList?.get(0)?.name)
    }

    companion object {
        const val PARTITION = 0
    }
}

private val om = ObjectMapper()
private fun BaseMessage.toJson(): String = om.writeValueAsString(this)
private inline fun <reified T: BaseMessage> String.fromJson() = om.readValue<T>(this, T::class.java)
