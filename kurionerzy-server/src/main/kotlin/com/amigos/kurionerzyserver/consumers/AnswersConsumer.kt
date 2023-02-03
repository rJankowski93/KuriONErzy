package com.amigos.kurionerzyserver.consumers

import com.amigos.kurionerzyserver.QuestionService
import com.amigos.kurionerzyserver.domain.Answer
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class AnswersConsumer(
    private val questionService: QuestionService,
) {

    @KafkaListener(topics = ["answers"], groupId = "kurionerzy", containerFactory = "answerContainerFactory")
    fun readAnswer(@Payload answer: Answer, @Header(KafkaHeaders.RECEIVED_KEY) key: String) {
        println("Received answer: $answer for user $key")
        //sprawdzenie czy odp jest poprawna
        questionService.isAnswerCorrect(answer.questionId, answer.answer)

        //sprawdzenie czy odp jest poprawna
        //zliczenie poprawnych odp i zapis w pamieci
    }
}