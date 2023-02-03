package com.amigos.kurionerzyserver.consumers

import com.amigos.kurionerzyserver.User
import com.amigos.kurionerzyserver.producers.QuestionProducerService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class UserConsumerService(
    val questionProducerService: QuestionProducerService
) {
    var numberOfPlayers = 2
    var usersSet: MutableSet<User> = mutableSetOf()
    //var isGameStarted: Boolean = false

    @KafkaListener(topics = ["users"], groupId = "users-consumers")
    fun consumeUsers(user: User) {
        if (!isRoomFull()) {
            usersSet.add(user)
            println("User added to game")
            if (isRoomFull()) {
                println("Game started")
                questionProducerService.startGame()
            }
        } else {
            // dodać handlowanie resulta, wtedy resetować pokój
            print("Game already started")
        }
    }

    private fun isRoomFull(): Boolean = usersSet.size >= numberOfPlayers
}


