package com.amigos.kurionerzyserver.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Answer(
    @JsonProperty("answer")
    val answer: String,
    @JsonProperty("questionId")
    val questionId: String,
)