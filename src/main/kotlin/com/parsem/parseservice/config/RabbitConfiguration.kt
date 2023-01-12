package com.parsem.parseservice.config

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.String


@Configuration
class RabbitConfiguration {

    @Value("\${spring.rabbitmq.host}")
    private val host: String = ""

    @Value("\${spring.rabbitmq.username}")
    private val username: String = ""

    @Value("\${spring.rabbitmq.password}")
    private val password: String = ""

    @Value("\${spring.rabbitmq.template.default-receive-queue}")
    private val qName: String = ""

    @Value("\${spring.rabbitmq.template.exchange}")
    private val exchange: String = ""


    @Value("\${spring.rabbitmq.template.routing-key}")
    private val routingKey: String = ""

    @Value("\${spring.rabbitmq.template.vhost}")
    private val vHost: String = ""

    @Bean
    fun connectionFactory(): ConnectionFactory {
        var cachingConnectionFactory = CachingConnectionFactory(host);
        cachingConnectionFactory.username = username;
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.virtualHost = vHost;
        return cachingConnectionFactory;
    }

    @Bean
    fun amqpAdmin(): AmqpAdmin {
        return RabbitAdmin(connectionFactory());
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        return RabbitTemplate(connectionFactory());
    }

    @Bean
    fun myQueue(): Queue {
        return Queue(qName);
    }

    @Bean
    fun exchange(): DirectExchange {
        return DirectExchange(exchange, true, false);
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder.bind(myQueue()).to(exchange()).with(routingKey);
    }
}