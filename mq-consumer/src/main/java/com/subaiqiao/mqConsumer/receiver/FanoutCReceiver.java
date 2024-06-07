package com.subaiqiao.mqConsumer.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.C")
public class FanoutCReceiver {

    @RabbitHandler
    public void process(String message) {
        System.out.println("FanoutCReceiver消费者收到消息：" + message);
    }
}
