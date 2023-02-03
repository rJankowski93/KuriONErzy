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
    ) // todo change groupId
    override fun subscribeQuestions(message: Question) {
        question = message
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
