package com.amigos.kurionerzyserver.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "questions")
data class QuestionsConfig(
    val questions: List<Question> = emptyList()
) {
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
}