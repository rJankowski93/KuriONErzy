package com.amigos.kurionerzyserver.consumers

import org.springframework.kafka.annotation.KafkaListener

class AnswersConsumer {

    @KafkaListener(topics = ["answers"], groupId = "kurionerzy")
    fun listenGroupFoo(message: String) {
        println("Received answer: $message")
    }
}