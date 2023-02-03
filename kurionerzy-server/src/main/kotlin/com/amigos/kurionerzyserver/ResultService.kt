package com.amigos.kurionerzyserver

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ResultService(
    val kafkaTemplate: KafkaTemplate<String, GameResult>) {

    fun sendResult(resultsGames: GameResult) {
        println("sent result: $resultsGames")
        kafkaTemplate.send("results", resultsGames)
    }
}
