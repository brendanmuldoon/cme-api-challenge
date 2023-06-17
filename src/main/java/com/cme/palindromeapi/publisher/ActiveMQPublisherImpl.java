package com.cme.palindromeapi.publisher;

import com.cme.palindromeapi.model.StoredTextValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMQPublisherImpl implements Publisher {

    @Override
    public void sendMessage(StoredTextValue storedTextValue) {
        log.info("Publishing event to queue");
        // activemq
        log.info("Event successfully published");



    }
}
