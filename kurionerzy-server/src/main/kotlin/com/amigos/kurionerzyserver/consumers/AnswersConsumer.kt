package com.amigos.kurionerzyserver.consumers

import com.amigos.kurionerzyserver.GameResult
import com.amigos.kurionerzyserver.QuestionService
import com.amigos.kurionerzyserver.ResultService
import com.amigos.kurionerzyserver.domain.Answer
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class AnswersConsumer(
    private val questionService: QuestionService,
    private val resultService: ResultService,
) {

    data class Result(
        val questionId: String,
        val isAnswerCorrect: Boolean,
    )

    private val answers = mutableMapOf<String, MutableList<Result>>()

    @KafkaListener(topics = ["answers"], groupId = "kurionerzy", containerFactory = "answerContainerFactory")
    fun readAnswer(@Payload answer: Answer, @Header(KafkaHeaders.RECEIVED_KEY) userId: String) {
        println("Received answer: $answer for user $userId")
        //sprawdzenie czy odp jest poprawna
        val correct = questionService.isAnswerCorrect(answer.questionId, answer.answer)
        incrementPointsForUser(answer.questionId, userId, correct)
        //zliczenie poprawnych odp i zapis w pamieci
        if (answers[userId]?.size!! >= 3) {
            val result = GameResult(
                getPointsForUser(userId).toString(),
                getBestScore().second.toString(),
                setOf(getBestScore().first.toString()),
            )
            resultService.sendResult(result)
        }
    }

    private fun incrementPointsForUser(questionId: String, userId: String, correct: Boolean) {
        if (answers.containsKey(userId)) {
            answers[userId]?.add(Result(questionId, correct))
        } else {
            answers.put(userId, mutableListOf())
        }
    }

    private fun getPointsForUser(userId: String) =
        answers[userId]?.filter(Result::isAnswerCorrect)?.size ?: 0

    private fun getBestScore(): Pair<String, Int> =
        answers.map {
            it.key to it.value.sumBy { if (it.isAnswerCorrect) 1 else 0 }
        }.sortedBy { it.second }.first()

}