package com.cme.palindromeapi.publisher;

import com.cme.palindromeapi.exception.GenericException;
import com.cme.palindromeapi.model.StoredTextValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@Slf4j
public class ActiveMQPublisherImpl implements Publisher {

    private final Queue queue;

    private final JmsTemplate jmsTemplate;

    public ActiveMQPublisherImpl(Queue queue, JmsTemplate jmsTemplate) {
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;

    }

    @Override
    public void sendMessage(StoredTextValue storedTextValue) {
        log.info("Publishing event to queue");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(storedTextValue);
            jmsTemplate.convertAndSend(queue, message);
            log.info("Event successfully published");
        } catch (JsonProcessingException ex) {
            throw new GenericException(ex.getMessage());
        }
    }
}
