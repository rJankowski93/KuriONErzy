package com.amigos.kurionerzyclient.domain

import com.amigos.kurionerzyclient.logger
import org.springframework.stereotype.Component

@Component
class GameService(
    private val producer: Producer
) {

    companion object {
        val logger by logger()
    }

    fun joinGame(user: User) =
        producer.sendMessage("users", user).also {
            logger.info("Joining the game as ${user.name}")
        }
}

data class User(val name: String)
