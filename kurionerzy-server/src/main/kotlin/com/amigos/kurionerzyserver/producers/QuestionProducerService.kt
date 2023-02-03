package com.amigos.kurionerzyserver.producers

import com.amigos.kurionerzyserver.QuestionService
import com.amigos.kurionerzyserver.config.QuestionsConfig
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class QuestionProducerService(
    val questionService: QuestionService,
    val kafkaTemplate: KafkaTemplate<String, QuestionsConfig.Question>
) {
    private val topicName = "questions"
    fun sendQuestions() {
        for (i in 1..5) {
            kafkaTemplate.send(topicName, questionService.getNextQuestion())
        }
    }

    fun startGame() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
//                stopWatch.stop()
                println("Function in scheduleWithTimer executed with delay ")
                sendQuestions()
//                timer.cancel() // this
            }
        }, 0, 5000)
    }


}
