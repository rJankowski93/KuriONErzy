package com.amigos.kurionerzyserver.consumers

import com.amigos.kurionerzyserver.User
import com.amigos.kurionerzyserver.producers.QuestionProducerService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service

@Service
class UserConsumerService(
    val questionProducerService: QuestionProducerService
) {
    var usersSet: MutableSet<User> = mutableSetOf()
    var isGameStarted: Boolean = false

    @KafkaListener(topics = ["users"], groupId = "users-consumers")
    fun consumeUsers(@Payload user: User, @Header(KafkaHeaders.RECEIVED_KEY) key: String) {
        if (!isGameReady()) {
            usersSet.add(user)
        } else if (!isGameStarted) {
            print("Players limit exceeded. Starting the game!")
            questionProducerService.startGame()
        } else {
            print("Game already started")
        }
    }
    private fun isGameReady(): Boolean = usersSet.size >= 2
}


