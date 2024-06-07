package com.subaiqiao.mqProvider.controller;

import com.alibaba.fastjson2.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class SendMessageController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public Map<String, Object> createMessage(String message) {
        String messageId = UUID.randomUUID().toString();
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("message", message);
        map.put("sendTime", date);
        return map;
    }

    @GetMapping("/direct/send")
    public String directSendMessage() {
        String message = "这是测试内容";
        Map<String, Object> map = createMessage(message);
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", JSON.toJSONString(map));
        return "ok";
    }

    @GetMapping("/topic/send1")
    public String topicSendMessage1() {
        String message = "message: MAN";
        Map<String, Object> map = createMessage(message);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", JSON.toJSONString(map));
        return "ok";
    }

    @GetMapping("/topic/send2")
    public String topicSendMessage2() {
        String message = "message: WOMAN is all";
        Map<String, Object> map = createMessage(message);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", JSON.toJSONString(map));
        return "ok";
    }

}
