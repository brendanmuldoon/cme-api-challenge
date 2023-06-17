package com.cme.palindromeapi.publisher;

import com.cme.palindromeapi.exception.GenericException;
import com.cme.palindromeapi.model.StoredTextValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@Slf4j
public class ActiveMQPublisherImpl implements Publisher {

    private final Queue queue;

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper mapper;

    public ActiveMQPublisherImpl(Queue queue, JmsTemplate jmsTemplate, ObjectMapper mapper) {
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
        this.mapper = mapper;

    }

    @Override
    public void sendMessage(StoredTextValue storedTextValue) {
        log.info("Publishing event to queue");
        try {
            String message = mapper.writeValueAsString(storedTextValue);
            jmsTemplate.convertAndSend(queue, message);
            log.info("Event successfully published");
        } catch (JsonProcessingException | JmsException ex) {
            throw new GenericException(ex.getMessage());
        }
    }
}
