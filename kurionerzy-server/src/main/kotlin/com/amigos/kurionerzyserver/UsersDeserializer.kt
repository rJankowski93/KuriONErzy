package com.amigos.kurionerzyserver

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer

class UsersDeserializer<T> : Serializer<T> {
    override fun serialize(topic: String?, data: T): ByteArray {
        val objectMapper = ObjectMapper()
        val bytes: ByteArray
        bytes = try {
            objectMapper.writeValueAsBytes(data)
        } catch (e: JsonProcessingException) {
            throw RuntimeException("Serialize error")
        }

        return bytes
    }
}
