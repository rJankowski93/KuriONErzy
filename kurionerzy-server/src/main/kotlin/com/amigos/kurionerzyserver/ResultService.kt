package com.amigos.kurionerzyserver

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ResultService(val kafkaTemplate: KafkaTemplate<String, ResultsGame>) {

    fun sendResult(resultsGames: ResultsGame) {
        kafkaTemplate.send("results", resultsGames)
    }
}