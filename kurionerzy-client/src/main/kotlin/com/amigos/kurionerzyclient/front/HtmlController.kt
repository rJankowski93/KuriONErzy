package com.amigos.kurionerzyclient.front

import com.amigos.kurionerzyclient.infastructure.MessageConsumer
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class HtmlController(val messageConsumer: MessageConsumer) {

    @GetMapping("/")
    fun blog(model: Model): String {
        return blog(null, model)
    }

    @GetMapping("/{answer}")
    fun blog(@PathVariable answer: String?, model: Model): String {
        val currentQuestion = messageConsumer.getCurrentQuestion()
        var message = ""
        if (answer != null) {
            if (answer == currentQuestion?.correctAnswer) {
                message = "Poprawna odpowiedz"
            } else {
                message = "Nie poprawna odpowiedz"
            }
        } else {
            message = "TO pierwsze pytanie"
        }

        if (currentQuestion == null) {
            message += "Czekaj na pytanie"
        } else {
            model["question"] = currentQuestion
        }
        model["message"] = message
        return "blog"
    }
}
