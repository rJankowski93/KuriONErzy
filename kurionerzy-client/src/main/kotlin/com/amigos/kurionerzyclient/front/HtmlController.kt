package com.amigos.kurionerzyclient.front

import com.amigos.kurionerzyclient.infastructure.PossibleAnswers
import com.amigos.kurionerzyclient.infastructure.Question
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {

    @GetMapping("/")
    fun blog(model: Model): String {
        val possibleAnswers = PossibleAnswers("Test1", "Test2", "Test3", "Test4")
        val question = Question("1", "Jaki masz kolor?", possibleAnswers, "C")
        model["question"] = question
        return "blog"
    }
}
