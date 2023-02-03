package com.amigos.kurionerzyclient.infastructure

import com.amigos.kurionerzyclient.domain.Consumer
import com.amigos.kurionerzyclient.logger
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class MessageConsumer : Consumer {

    companion object {
        val logger by logger()
        var question: Question? = null
    }

    fun getCurrentQuestion(): Question? {
        val currQuestion = question
        question = null
        return currQuestion
    }

    @KafkaListener(
        topics = ["questions"],
        containerFactory = "questionContainerFactory"
    )
    override fun subscribeQuestions(message: Question) {
        question = message
    }

    @KafkaListener(
        topics = ["results"],
        containerFactory = "resultContainerFactory"
    )
    override fun subscribeResults(message: GameResult) {
        logger.info(
            """
            End of the game!
            Your score is: ${message.score}.
            Best score is: ${message.bestScore}.
            Winner(s) is/are: ${message.bestPlayers}
            """.trimIndent()
        )
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Question(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("text")
    val text: String,
    @JsonProperty("possibleAnswers")
    val possibleAnswers: PossibleAnswers,
    @JsonProperty("correctAnswer")
    val correctAnswer: String
)

data class PossibleAnswers(
    @JsonProperty("A")
    val A: String,
    @JsonProperty("B")
    val B: String,
    @JsonProperty("C")
    val C: String,
    @JsonProperty("D")
    val D: String
)

data class GameResult(
    @JsonProperty("score")
    val score: String,
    @JsonProperty("bestScore")
    val bestScore: String,
    @JsonProperty("bestPlayers")
    val bestPlayers: Set<String>
)
