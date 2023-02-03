package com.amigos.kurionerzyclient.config

import com.amigos.kurionerzyclient.infastructure.GameResult
import com.amigos.kurionerzyclient.infastructure.Question
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import java.io.IOException
import java.nio.charset.Charset

class QuestionDeserializer : Deserializer<Question> {
    override fun deserialize(topic: String?, data: ByteArray?): Question {
        val objectMapper = ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return try {
            objectMapper.readValue(String(data!!, Charset.defaultCharset()), Question::class.java)
        } catch (e: IOException) {
            throw RuntimeException("Serialize error")
        }
    }
}

class ResultDeserializer : Deserializer<GameResult> {
    override fun deserialize(topic: String?, data: ByteArray?): GameResult {
        val objectMapper = ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        return try {
            objectMapper.readValue(String(data!!, Charset.defaultCharset()), GameResult::class.java)
        } catch (e: IOException) {
            throw RuntimeException("Serialize error")
        }
    }
}
