package com.amigos.kurionerzyserver

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.protocol.types.Field.Bool
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserConsumerService {

    var usersSet: MutableSet<User> = mutableSetOf()

    private final val topicName = "users"
    private final val consumerProps = { group: String ->
        mapOf(
            "bootstrap.servers" to "localhost:9092",
            "auto.offset.reset" to "earliest",
            "key.deserializer" to "org.apache.kafka.common.serialization.StringDeserializer",
            "value.deserializer" to "org.apache.kafka.common.serialization.CustomSerializer",
            "group.id" to group,
            "security.protocol" to "PLAINTEXT"
        )
    }

    val usersConsumerTopic = KafkaConsumer<String, User>(consumerProps("simple-consumer"))

    private fun consumeUsers() {
        usersConsumerTopic.use {
            it.subscribe(listOf(topicName))
            while (true) {
                val records = it.poll(Duration.ofSeconds(1))
                for (record in records) {
                    usersSet.add(record.value())
                }

            }
        }
    }
    private fun checkIfGameReady(): Boolean = usersSet.size > 2
}
