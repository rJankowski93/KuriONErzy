package com.amigos.kurionerzyclient.api

import com.amigos.kurionerzyclient.domain.Answer
import com.amigos.kurionerzyclient.domain.GameService
import com.amigos.kurionerzyclient.domain.User
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/game")
class GameEndpoints(
    private val gameService: GameService
) {

    @PostMapping(value = ["/join"])
    fun joinGame(
        @RequestBody userRequest: UserRequest
    ) {
        gameService.joinGame(userRequest.toDomain())
    }

    @PostMapping(value = ["/answer"])
    fun answerQuestion(
        @RequestBody answerRequest: AnswerRequest
    ) {
        gameService.answerQuestion(answerRequest.toDomain())
    }
}

data class UserRequest(
    @JsonProperty("id") val id: String
)

fun UserRequest.toDomain() = User(
    id = id
)

data class AnswerRequest(
    @JsonProperty("id") val id: String
)

fun AnswerRequest.toDomain() = Answer(
    id = id
)
