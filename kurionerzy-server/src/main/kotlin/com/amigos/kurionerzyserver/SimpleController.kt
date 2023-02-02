package com.amigos.kurionerzyserver

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.time.Duration


@Controller
class SimpleController {

    @GetMapping("/produce")
    fun produce(): String {

        val producerProps = mapOf(
            "bootstrap.servers" to "localhost:9092",
            "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
            "value.serializer" to "org.apache.kafka.common.serialization.ByteArraySerializer",
            "security.protocol" to "PLAINTEXT"
        )

        KafkaProducer<String, ByteArray>(producerProps).use {
            it.send(ProducerRecord("test", "1", "Hello, world!".encodeToByteArray()))
        }

        return "example"
    }

    @GetMapping("/consume")
    fun consume(): String {

        val consumerProps = { group: String ->
            mapOf(
                "bootstrap.servers" to "localhost:9092",
                "auto.offset.reset" to "earliest",
                "key.deserializer" to "org.apache.kafka.common.serialization.StringDeserializer",
                "value.deserializer" to "org.apache.kafka.common.serialization.ByteArrayDeserializer",
                "group.id" to group,
                "security.protocol" to "PLAINTEXT"
            )
        }

        KafkaConsumer<String, ByteArray>(consumerProps("simple-consumer")).use {
            it.subscribe(listOf("test"))
            while (true) {
                val records = it.poll(Duration.ofSeconds(1))
                for (record in records) {
                    print(record)
                }
            }
        }

        return "example"
    }
}