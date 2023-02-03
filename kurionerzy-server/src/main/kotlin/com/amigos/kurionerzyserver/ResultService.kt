package com.amigos.kurionerzyserver

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.stereotype.Component

@Component
class ResultService {

    fun sendResult(resultsGames: ResultsGame) {
        val producerProps = mapOf(
            "bootstrap.servers" to "localhost:9092",
            "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
            "value.serializer" to "com.amigos.kurionerzyserver.CustomSerializer",
            "security.protocol" to "PLAINTEXT"
        )

        KafkaProducer<String, ResultsGame>(producerProps).use {
            it.send(ProducerRecord("results", resultsGames))
        }
    }
}