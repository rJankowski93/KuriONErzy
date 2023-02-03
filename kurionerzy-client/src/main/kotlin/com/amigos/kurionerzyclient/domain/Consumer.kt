package com.amigos.kurionerzyclient.domain

import com.amigos.kurionerzyclient.infastructure.GameResult
import com.amigos.kurionerzyclient.infastructure.Question

interface Consumer {

    fun subscribeQuestions(message: Question)

    fun subscribeResults(message: GameResult)
}
