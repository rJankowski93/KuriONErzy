package com.amigos.kurionerzyclient.infastructure

import com.amigos.kurionerzyclient.domain.Consumer
import com.amigos.kurionerzyclient.logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class MessageConsumer : Consumer {

    companion object {
        val logger by logger()
        val questions = ConcurrentHashMap<String, Question>()
    }

    @KafkaListener(topics = ["questions"], groupId = "kurionerzy") // todo change groupId
    override fun subscribeQuestions(message: Question) {
        questions[message.id] = message
        if (questions.size == 1) {
            logger.info("First question: ${message.text}")
        } else {
            logger.info("Next question: ${message.text}")
        }

        logger.info(
            """
            Answers:
                a) ${message.possibleAnswers.A},
                b) ${message.possibleAnswers.B},
                c) ${message.possibleAnswers.C},
                d) ${message.possibleAnswers.D},
            """.trimIndent()
        )
    }
}

data class Question(
    val id: String,
    val text: String,
    val possibleAnswers: PossibleAnswers,
    val correctAnswer: String
)

data class PossibleAnswers(
    val A: String,
    val B: String,
    val C: String,
    val D: String
)
