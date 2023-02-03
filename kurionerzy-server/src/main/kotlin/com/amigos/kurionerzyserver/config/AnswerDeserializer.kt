package com.amigos.kurionerzyserver.config

import com.amigos.kurionerzyserver.domain.Answer
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import java.io.IOException
import java.nio.charset.Charset

class AnswerDeserializer : Deserializer<Answer> {
    override fun deserialize(topic: String?, data: ByteArray?): Answer {
        val objectMapper = ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return try {
            objectMapper.readValue(String(data!!, Charset.defaultCharset()), Answer::class.java)
        } catch (e: IOException) {
            throw RuntimeException("Serialize error")
        }
    }
}