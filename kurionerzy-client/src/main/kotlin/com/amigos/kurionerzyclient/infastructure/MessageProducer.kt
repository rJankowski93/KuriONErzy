package com.amigos.kurionerzyclient.infastructure

import com.amigos.kurionerzyclient.domain.Producer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.stereotype.Component

@Component
class MessageProducer : Producer {

    companion object {
        private val producerProps = mapOf(
            "bootstrap.servers" to "localhost:9092",
            "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
            "value.serializer" to "com.amigos.kurionerzyclient.infastructure.CustomSerializer",
            "security.protocol" to "PLAINTEXT"
        )

        val producer = KafkaProducer<String, Any>(producerProps)
    }

    override fun sendMessage(topic: String, message: Any) {
        producer.send(ProducerRecord("users", message))
    }
}
