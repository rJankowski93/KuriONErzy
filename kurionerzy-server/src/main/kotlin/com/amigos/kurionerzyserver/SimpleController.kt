package com.amigos.kurionerzyserver

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.time.Duration

@Controller
class SimpleController(val resultService: ResultService) {

    @GetMapping("/produce")
    fun produce(): String {
        // Test sendResult
        val result1 = ResultGame("Pawel", 12)
        val result2 = ResultGame("Michal", 15)
        val results = ResultsGame(listOf(result1, result2), "Michal")
        resultService.sendResult(results)

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
