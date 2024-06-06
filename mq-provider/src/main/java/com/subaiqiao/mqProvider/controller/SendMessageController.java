package com.subaiqiao.mqProvider.controller;

import com.alibaba.fastjson2.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class SendMessageController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String sendMessage() {
        String messageId = UUID.randomUUID().toString();
        String message = "这是测试内容";
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("message", message);
        map.put("sendTime", new Date());
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", JSON.toJSONString(map));
        return "ok";
    }
}
