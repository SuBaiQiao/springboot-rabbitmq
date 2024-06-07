package com.subaiqiao.mqProvider.exchange.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题交换机
 */
@Configuration
public class TopicRabbitConfig {
    public final static String man = "topic.man";
    public final static String woman = "topic.woman";

    @Bean
    public Queue firstQueue() {
        return new Queue(man);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(woman);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * 将firstQueue和topicExchange绑定，而且绑定的键值为topic.man
     * 这样只要消息携带的路由键是topic.man，才会发布到该队列中
     * @return 绑定结果
     */
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(firstQueue()).to(topicExchange()).with(man);
    }

    /**
     * 将secondQueue和topicExchange绑定，而且绑定的键值用上通配路键规则topic.#
     * 这样只要消息携带的路由键是以topic.开头，都会发布到该队列中
     * @return 绑定结果
     */
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(topicExchange()).with("topic.#");
    }

}
