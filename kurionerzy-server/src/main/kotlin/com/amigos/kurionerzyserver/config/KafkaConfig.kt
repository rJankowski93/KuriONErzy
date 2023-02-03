package com.amigos.kurionerzyserver.config

import com.amigos.kurionerzyserver.CustomSerializer
import com.amigos.kurionerzyserver.ResultsGame
import com.amigos.kurionerzyserver.domain.Answer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@Configuration
@EnableKafka
class KafkaConsumerConfig {

    @Value(value = "\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapAddress: String

    @Bean
    fun answerConsumerFactory(): ConsumerFactory<String, Answer> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.GROUP_ID_CONFIG] = "kurionerzy"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = AnswerDeserializer::class.java
        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun answerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Answer>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Answer>()
        factory.setConsumerFactory(answerConsumerFactory())
        return factory
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, ResultsGame> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = CustomSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun producerFactoryQuestions(): ProducerFactory<String, QuestionsConfig.Question> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = CustomSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, ResultsGame> {
        return KafkaTemplate(producerFactory())
    }

    @Bean
    fun kafkaTemplateQuestions(): KafkaTemplate<String, QuestionsConfig.Question> {
        return KafkaTemplate(producerFactoryQuestions())
    }
}
