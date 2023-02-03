package com.amigos.kurionerzyclient.domain

interface Producer {

    fun sendMessage(topic: String, message: Any)

    fun sendMessage(topic: String, key: String, message: Any)
}
