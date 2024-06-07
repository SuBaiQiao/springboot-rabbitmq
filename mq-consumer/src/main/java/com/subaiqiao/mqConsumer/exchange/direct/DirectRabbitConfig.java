package com.subaiqiao.mqConsumer.exchange.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 直连型交换机
 * 其实消费者不需要加这个，这个是为了可以发送消息
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * TestDirectQueue队列
     * @return 队列
     */
    @Bean
    public Queue TestDirectQueue() {
        // durable：是否持久化，默认是false。持久化队列：会被存储在磁盘上，当消息代理重启之后，依然会存在。暂存队列：当前连接有效
        // exclusive：默认是false。只能在当前创建的连接使用，关闭连接后删除。优先级高于durable。
        // autoDelete：是否自动删除，当没有生产者或消费者使用此队列，该队列会自动删除。

        // 一般情况下设置为持久话即可，剩下两个都是false。
        return new Queue("TestDirectQueue", false);
    }

    /**
     * TestDirectExchange 交换机
     * @return 交换机
     */
    @Bean
    DirectExchange TestDirectExchange() {
        return new DirectExchange("TestDirectExchange", true, false);
    }

    /**
     * 将队列和交换机绑定到路由，设置匹配键TestDirectRouting
     * @return 绑定
     */
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }
}
