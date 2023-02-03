package com.amigos.kurionerzyserver.consumers

import com.amigos.kurionerzyserver.domain.Answer
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class AnswersConsumer {

    @KafkaListener(topics = ["answers"], groupId = "kurionerzy")
    fun listenGroupFoo(@Payload answer: Answer, @Header(KafkaHeaders.RECEIVED_KEY) key: String) {
        println("Received answer: $answer for user $key")
    }
}