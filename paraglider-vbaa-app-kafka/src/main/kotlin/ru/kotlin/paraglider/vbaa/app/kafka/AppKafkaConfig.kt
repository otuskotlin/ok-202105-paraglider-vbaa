package ru.kotlin.paraglider.vbaa.app.kafka

import RepoSchoolInMemory
import SchoolService
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import ru.kotlin.paraglider.vbaa.be.common.context.ContextConfig
import ru.kotlin.paraglider.vbaa.be.logics.chains.school.SchoolCrudFacade
import java.time.Duration
import java.util.*

data class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaTopicIn: String = KAFKA_TOPIC_IN,
    val kafkaTopicOut: String = KAFKA_TOPIC_OUT,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val contextConfig: ContextConfig = ContextConfig(
        repoProd = RepoSchoolInMemory(initObjects = listOf(), ttl = Duration.ofHours(1)),
        repoTest = RepoSchoolInMemory(initObjects = listOf()),
    ),
    val service: SchoolService = SchoolService(SchoolCrudFacade()),
    val kafkaConsumer: Consumer<String, String> = kafkaConsumer(kafkaHosts, kafkaGroupId),
    val kafkaProducer: Producer<String, String> = kafkaProducer(kafkaHosts)
) {
    companion object {
        private const val KAFKA_HOST_ENV = "KAFKA_HOSTS"
        private const val KAFKA_TOPIC_IN_ENV = "KAFKA_TOPIC_IN"
        private const val KAFKA_TOPIC_OUT_ENV = "KAFKA_TOPIC_OUT"
        private const val KAFKA_GROUP_ID_ENV = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_ENV) ?: "localhost:9094").split("\\s*[,;]\\s*".toRegex()) }
        val KAFKA_TOPIC_IN by lazy { System.getenv(KAFKA_TOPIC_IN_ENV) ?: "prgldr-school-in" }
        val KAFKA_TOPIC_OUT by lazy { System.getenv(KAFKA_TOPIC_OUT_ENV) ?: "prgldr-school-out" }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_ENV) ?: "prgldr-school" }

        fun kafkaConsumer(hosts: List<String>, groupId: String): KafkaConsumer<String, String> {
            val props = Properties().apply {
                put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts)
                put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
                put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
                put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
            }
            return KafkaConsumer<String, String>(props)
        }

        fun kafkaProducer(hosts: List<String>): KafkaProducer<String, String> {
            val props = Properties().apply {
                put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts)
                put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
                put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            }
            return KafkaProducer<String, String>(props)
        }
    }
}