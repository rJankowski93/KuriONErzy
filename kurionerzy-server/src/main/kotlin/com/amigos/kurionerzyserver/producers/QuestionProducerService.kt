package com.amigos.kurionerzyserver.producers

import com.amigos.kurionerzyserver.QuestionService
import com.amigos.kurionerzyserver.config.QuestionsConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class QuestionProducerService(
    val questionService: QuestionService,
    @Qualifier("kafkaTemplateQuestions")
    val kafkaTemplate: KafkaTemplate<String, QuestionsConfig.Question>
) {
    private val topicName = "questions"
    fun sendQuestion() {
        kafkaTemplate.send(topicName, questionService.getNextQuestion())
    }

    fun startGame() {
        var counter = 0
        questionService.createQuestionPoll()
        Timer().schedule(object : TimerTask() {
            override fun run() {
//                stopWatch.stop()
                println("Function in scheduleWithTimer executed with delay ")
                sendQuestion()
                counter++
                if (counter >= 15) {
                    this.cancel()
                }

            }
        }, 0, 5000)
    }


}
