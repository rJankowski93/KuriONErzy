package com.amigos.kurionerzyclient.domain

interface Producer {

    fun sendMessage(topic: String, message: Any)
}
