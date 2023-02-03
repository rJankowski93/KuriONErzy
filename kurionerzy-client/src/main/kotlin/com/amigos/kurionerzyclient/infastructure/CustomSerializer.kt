package com.amigos.kurionerzyclient.infastructure

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer

class CustomSerializer<T> : Serializer<T> {
    override fun serialize(topic: String?, data: T): ByteArray {
        val objectMapper = ObjectMapper()
        val bytes: ByteArray = try {
            objectMapper.writeValueAsBytes(data)
        } catch (e: JsonProcessingException) {
            throw RuntimeException("Serialize error")
        }

        return bytes
    }
}
