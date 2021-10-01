package ru.kotlin.paraglider.vbaa.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config)
    consumer.run()
}