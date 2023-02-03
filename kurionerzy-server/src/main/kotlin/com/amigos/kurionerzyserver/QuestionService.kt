package com.amigos.kurionerzyserver

import com.amigos.kurionerzyserver.config.QuestionsConfig
import org.springframework.stereotype.Component

@Component
class QuestionService(val questionsConfig: QuestionsConfig) {

    var availableQuestions: MutableList<QuestionsConfig.Question> = emptyList<QuestionsConfig.Question>().toMutableList()
    var usedQuestions: MutableList<QuestionsConfig.Question> = emptyList<QuestionsConfig.Question>().toMutableList()

    fun createQuestionPoll() {
        availableQuestions.addAll(questionsConfig.questions)
    }

    fun getNextQuestion(): QuestionsConfig.Question? {
        val element = availableQuestions.asSequence().shuffled().find { true }
        if (element != null) {
            availableQuestions.remove(element)
            usedQuestions.add(element)
        }
        return element
    }
}