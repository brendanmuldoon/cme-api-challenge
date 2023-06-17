package com.cme.palindromeapi.listener;

import com.cme.palindromeapi.repository.PalindromeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ActiveMQListenerImplTest {

    @InjectMocks
    private ActiveMQListenerImpl activeMQListener;

    @Mock
    private PalindromeRepository repository;

    @Test
    @DisplayName("Given a message, then call the repository write method once")
    void testRetrieveMessageCallsRepo() {
        String message = "test";
        doNothing().when(repository).write(message);
        activeMQListener.retrieveMessage(message);
        verify(repository, times(1)).write(message);
    }

}