package com.amigos.kurionerzyclient.front

import com.amigos.kurionerzyclient.domain.Answer
import com.amigos.kurionerzyclient.domain.GameService
import com.amigos.kurionerzyclient.infastructure.MessageConsumer
import com.amigos.kurionerzyclient.infastructure.Question
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class HtmlController(val messageConsumer: MessageConsumer, val gameService: GameService) {

    var currentQuestion: Question? = null

    @GetMapping("/")
    fun blog(model: Model): String {
        return blog(null, model)
    }

    @GetMapping("/{answer}")
    fun blog(@PathVariable answer: String?, model: Model): String {
        if (answer != null && currentQuestion != null) {
            gameService.answerQuestion(Answer(answer, currentQuestion!!.id))
        }
        var message = ""
        if (answer != null) {
            if (answer == currentQuestion?.correctAnswer) {
                message = "Poprawna odpowiedz"
            } else {
                message = "Nie poprawna odpowiedz"
            }
        } else {
            message = "To pierwsze pytanie"
        }
        currentQuestion = messageConsumer.getCurrentQuestion()

        if (currentQuestion == null) {
            message += "\nCzekaj na pytanie"
        } else {
            model["question"] = currentQuestion!!
        }
        model["message"] = message
        return "blog"
    }
}
