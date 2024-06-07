package com.subaiqiao.mqProvider.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 开启之后才可以触发回调
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(jsonToMapMessageConverter());

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("ConfirmCallback相关数据：" + correlationData);
            System.out.println("ConfirmCallback确认情况：" + ack);
            System.out.println("ConfirmCallback原因：" + cause);
        });

        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("ReturnsCallback消息：" + returnedMessage.getMessage());
            System.out.println("ReturnsCallback回应码：" + returnedMessage.getReplyCode());
            System.out.println("ReturnsCallback回应消息：" + returnedMessage.getReplyText());
            System.out.println("ReturnsCallback交换机：" + returnedMessage.getExchange());
            System.out.println("ReturnsCallback路由键：" + returnedMessage.getRoutingKey());
        });

        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonToMapMessageConverter() {
        DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
        defaultClassMapper.setTrustedPackages("com.subaiqiao"); // trusted packages
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
        return jackson2JsonMessageConverter;
    }

}
