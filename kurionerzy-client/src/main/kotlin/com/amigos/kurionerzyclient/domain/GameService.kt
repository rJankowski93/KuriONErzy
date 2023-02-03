package com.amigos.kurionerzyclient.domain

import com.amigos.kurionerzyclient.infastructure.MessageConsumer
import com.amigos.kurionerzyclient.logger
import org.springframework.stereotype.Component

@Component
class GameService(
    private val producer: Producer
) {

    companion object {
        val logger by logger()
        var userId = ""
    }

    fun joinGame(user: User) =
        producer.sendMessage("users", user).also {
            logger.info("Joining the game as ${user.id}")
            userId = user.id
        }

    fun answerQuestion(answer: Answer) {
        producer.sendMessage(topic = "answers", key = userId, message = answer).also {
            if (answer.id.equals(MessageConsumer.questions.last().correctAnswer, ignoreCase = true)) {
                logger.info("Your answer ${answer.id} is correct!")
            } else {
                logger.info("Unfortunately you are wrong. The correct answer is ${MessageConsumer.questions.last().correctAnswer}.")
            }
        }
    }
}

data class User(val id: String)

data class Answer(val id: String)
