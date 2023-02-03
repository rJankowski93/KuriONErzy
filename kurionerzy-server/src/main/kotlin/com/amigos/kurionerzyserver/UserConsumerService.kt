package com.amigos.kurionerzyserver

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserConsumerService {
    val usersList: List<User> = mutableListOf()

    private final val topicName = "questions"
    private final val consumerProps = { group: String ->
        mapOf(
            "bootstrap.servers" to "localhost:9092",
            "auto.offset.reset" to "earliest",
            "key.deserializer" to "org.apache.kafka.common.serialization.StringDeserializer",
            "value.deserializer" to "org.apache.kafka.common.serialization.ByteArrayDeserializer",
            "group.id" to group,
            "security.protocol" to "PLAINTEXT"
        )
    }

    val usersConsumerTopic = KafkaConsumer<String, ByteArray>(consumerProps("simple-consumer")).use {
        it.subscribe(listOf(topicName))
        while (true) {
            val records = it.poll(Duration.ofSeconds(1))
            for (record in records) {
                print(record)
            }
        }
    }

    private fun consumeUsers(){

    }
}
