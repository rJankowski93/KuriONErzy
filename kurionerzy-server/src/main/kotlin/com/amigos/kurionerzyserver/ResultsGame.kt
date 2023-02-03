package com.amigos.kurionerzyserver

data class GameResult(
    val score: String,
    val bestScore: String,
    val bestPlayers: Set<String>
)