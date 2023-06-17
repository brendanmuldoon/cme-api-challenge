package com.cme.palindromeapi.publisher;

import com.cme.palindromeapi.exception.GenericException;
import com.cme.palindromeapi.model.StoredTextValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ActiveMQPublisherImplTest {

    @InjectMocks
    private ActiveMQPublisherImpl activeMQPublisher;

    @Mock
    private Queue queue;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private JmsTemplate template;

    @Test
    @DisplayName("When given a valid object then call convertAndSend once")
    void testSendMessageSuccess() throws JsonProcessingException {
        StoredTextValue storedTextValue = new StoredTextValue("test", true);
        when(mapper.writeValueAsString(storedTextValue)).thenReturn("test");
        doNothing().when(template).convertAndSend(any(Queue.class), anyString());
        activeMQPublisher.sendMessage(storedTextValue);
        verify(template, times(1)).convertAndSend(any(Queue.class), anyString());

    }

    @Test
    @DisplayName("When a JsonProcessingException is thrown then throw GenericException")
    void testSendMessageFailJsonGenericException() throws JsonProcessingException {
        StoredTextValue storedTextValue = new StoredTextValue("test", true);
        doThrow(JsonProcessingException.class).when(mapper).writeValueAsString(any());
        try {
            activeMQPublisher.sendMessage(storedTextValue);
        } catch (GenericException ex) {
            assertTrue(true);
            return;
        }
        fail("Expected GenericException to be thrown.");

    }


}