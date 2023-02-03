package com.amigos.kurionerzyserver

import com.amigos.kurionerzyserver.config.QuestionsConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(QuestionsConfig::class)
class KurionerzyServerApplication

fun main(args: Array<String>) {
    runApplication<KurionerzyServerApplication>(*args)
}
