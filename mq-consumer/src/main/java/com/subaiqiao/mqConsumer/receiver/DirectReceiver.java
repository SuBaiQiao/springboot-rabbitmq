package com.subaiqiao.mqConsumer.receiver;

import com.alibaba.fastjson2.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 监听mq
 */
@Component
@RabbitListener(queues = "TestDirectQueue")
public class DirectReceiver {

    @RabbitHandler
    public void process(String testMessage) {
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage);
        Map<String, Object> map = JSON.parseObject(testMessage);
        System.out.println(map.get("messageId"));
    }

}
