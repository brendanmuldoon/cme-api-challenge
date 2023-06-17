package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;
import com.cme.palindromeapi.model.StoredTextValue;
import com.cme.palindromeapi.publisher.Publisher;
import com.cme.palindromeapi.util.RequestValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class PalindromeServiceImplTest {

    @Mock
    private RequestValidator requestValidator;
    @Mock
    private CacheService cacheService;
    @Mock
    private Publisher publisher;
    @InjectMocks
    private PalindromeServiceImpl service;

    @Test
    @DisplayName("Given an existing processed value return its palindrome status as true")
    void testCheckIsPalindromeExistingSuccess() throws JsonProcessingException {
        RequestObject request = generateRequest("username", "kayak");
        StoredTextValue storedTextValue = generateResponse("kayak", true);
        doNothing().when(requestValidator).isValid(request);
        when(cacheService.findByKey(request.getTextValue())).thenReturn(storedTextValue);
        ResponseObject response = service.checkIsPalindrome(request);
        testAssertions(response, "true");

    }


    @Test
    @DisplayName("Given an non-existing processed value return its palindrome status as true")
    void testCheckIsPalindromeNotExistingSuccessTrue() throws JsonProcessingException {
        RequestObject request = generateRequest("username", "kayak");
        doNothing().when(requestValidator).isValid(request);
        when(cacheService.findByKey(request.getTextValue())).thenReturn(null);
        doNothing().when(cacheService).storeProcessedValue(any());
        doNothing().when(publisher).sendMessage(any());
        ResponseObject response = service.checkIsPalindrome(request);
        testAssertions(response, "true");

    }

    @Test
    @DisplayName("Given an non-existing processed value return its palindrome status as false")
    void testCheckIsPalindromeNotExistingSuccessFalse() throws JsonProcessingException {
        RequestObject request = generateRequest("username", "test");
        doNothing().when(requestValidator).isValid(request);
        when(cacheService.findByKey(request.getTextValue())).thenReturn(null);
        doNothing().when(cacheService).storeProcessedValue(any());
        doNothing().when(publisher).sendMessage(any());
        ResponseObject response = service.checkIsPalindrome(request);
        testAssertions(response, "false");
    }

    private void testAssertions(ResponseObject response, String isPalindrome) {
        assertNotNull(response);
        assertEquals(String.format("isPalindrome: %s", isPalindrome), response.getData());
        assertEquals(200, response.getCode());
        assertEquals(HttpStatus.OK, response.getHttpStatus());
    }


    private StoredTextValue generateResponse(String textValue, boolean isPalindrome) {
        return new StoredTextValue(textValue, isPalindrome);
    }

    private RequestObject generateRequest(String username, String textValue) {
        return new RequestObject(username, textValue);
    }
}