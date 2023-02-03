package com.amigos.kurionerzyserver

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ResultService(
    @Qualifier("kafkaTemplate")
    val kafkaTemplate: KafkaTemplate<String, ResultsGame>) {

    fun sendResult(resultsGames: ResultsGame) {
        kafkaTemplate.send("results", resultsGames)
    }
}
