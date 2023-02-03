package com.amigos.kurionerzyserver.producers

import com.amigos.kurionerzyserver.consumers.UserConsumerService
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*
import kotlin.concurrent.timer

@Service
class QuestionProducerService(
    // dependencja od damiana
    val usersService: UserConsumerService,
    val kafkaTemplate: KafkaTemplate<String, String> // Question zamiast String
) {

    fun sendQuestions(question: String) { // Question od Damiana
        for (i in 1..5) {
            kafkaTemplate.send("questions", question)
        }
    }

    fun startGame() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
//                stopWatch.stop()
                println("Function in scheduleWithTimer executed with delay ")
                sendQuestions("TEST_QUESTION") // od Damiana
//                timer.cancel() // this
            }
        }, 0, 5000)
    }


}
