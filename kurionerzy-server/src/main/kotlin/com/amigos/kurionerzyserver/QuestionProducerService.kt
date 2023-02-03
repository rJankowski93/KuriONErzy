package com.amigos.kurionerzyserver

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.stereotype.Service

@Service
class QuestionProducerService(
    // dependencja od damiana
    val usersService: UserConsumerService
) {
    private final val topicName = "questions"

    private final val producerProps = mapOf(
        "bootstrap.servers" to "localhost:9092",
        "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
        "value.serializer" to "org.apache.kafka.common.serialization.ByteArraySerializer",
        "security.protocol" to "PLAINTEXT"
    )

    val questionsProducerTopic = KafkaProducer<String, ByteArray>(producerProps)

    //Adnotacja springowa
    fun sendQuestion() {
        //dependencja od damiana.losujPytanie()
        //zwraca pytanie
        questionsProducerTopic.use {
            it.send(
                ProducerRecord(
                    topicName,
                    "question", // numer pytania od damiana albo null
                    "QuestionBody".encodeToByteArray()
                )
            ) // serializacja do Question
        }
    }
}
