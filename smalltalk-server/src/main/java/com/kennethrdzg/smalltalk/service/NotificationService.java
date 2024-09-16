package com.kennethrdzg.smalltalk.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService{
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    
    private RabbitTemplate rabbitTemplate;

    public NotificationService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(String routingKey, String object){
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }
}