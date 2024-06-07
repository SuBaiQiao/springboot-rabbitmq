package com.subaiqiao.mqConsumer.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.B")
public class FanoutBReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("FanoutBReceiver消费者收到消息：" + message);
    }
}
