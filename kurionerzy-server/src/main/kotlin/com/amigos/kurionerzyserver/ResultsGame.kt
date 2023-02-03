package com.amigos.kurionerzyserver

data class ResultsGame(
    val results: List<ResultGame>,
    val winner: String
)

data class ResultGame(
    val name: String,
    val points: Int
)