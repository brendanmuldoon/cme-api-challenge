package com.cme.palindromeapi.listener;

import com.cme.palindromeapi.repository.PalindromeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMQListenerImpl implements Listener {

    private final PalindromeRepository palindromeRepository;

    public ActiveMQListenerImpl(PalindromeRepository palindromeRepository) {
        this.palindromeRepository = palindromeRepository;

    }

    @Override
    @JmsListener(destination = "local.in-memory.palindrome.queue")
    public void retrieveMessage(String message) {
        log.info("Received message : {}", message);
        palindromeRepository.write(message);
    }
}
