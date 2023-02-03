package com.amigos.kurionerzyclient.config

import com.amigos.kurionerzyclient.infastructure.GameResult
import com.amigos.kurionerzyclient.infastructure.Question
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
@EnableKafka
class KafkaConsumerConfig {

    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapAddress: String

    @Value(value = "\${consumer.group.id}")
    private lateinit var consumerGroupId: String

    fun questionConsumerFactory(): ConsumerFactory<String, Question> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = "questions$consumerGroupId"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = QuestionDeserializer::class.java
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun questionContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Question>? =
        ConcurrentKafkaListenerContainerFactory<String, Question>().also {
            it.consumerFactory = questionConsumerFactory()
        }

    fun resultConsumerFactory(): ConsumerFactory<String, GameResult> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = "results$consumerGroupId"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ResultDeserializer::class.java
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun resultContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, GameResult>? =
        ConcurrentKafkaListenerContainerFactory<String, GameResult>().also {
            it.consumerFactory = resultConsumerFactory()
        }
}
