package com.cme.palindromeapi.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@Configuration
@EnableJms
public class ActiveMQConfig {

    @Value("${spring.activemq.queue.name}")
    private String queueName;

    @Bean
    public Queue createQueue(){

        return new ActiveMQQueue(queueName);

    }
}
