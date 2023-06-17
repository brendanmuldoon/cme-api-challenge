package com.cme.palindromeapi.util;

import com.cme.palindromeapi.config.PatternConfig;
import com.cme.palindromeapi.exception.ValidationException;
import com.cme.palindromeapi.model.RequestObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RequestValidatorImplTest {

    @Mock
    private PatternConfig patternConfig;

    @InjectMocks
    private RequestValidatorImpl requestValidator;

    @Test
    @DisplayName("Throw a validation exception due to null request")
    void testNullRequest() {

        try {
            requestValidator.isValid(null);
        } catch (ValidationException ex) {
            assertTrue(true);
            assertEquals("Request cannot be null", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    @DisplayName("Throw a validation exception due to null text value")
    void testTextTextValue() {
        RequestObject requestObject = new RequestObject();
        try {
            requestValidator.isValid(requestObject);
        } catch (ValidationException ex) {
            assertTrue(true);
            assertEquals("Request textValue cannot be null", ex.getMessage());
            return;
        }
        fail();
    }

    @Test
    @DisplayName("Throw a validation exception due to blank text value")
    void testNullBlankValue() {
        RequestObject requestObject = new RequestObject();
        requestObject.setTextValue(" ");
        try {
            requestValidator.isValid(requestObject);
        } catch (ValidationException ex) {
            assertTrue(true);
            assertEquals("Request textValue cannot be blank", ex.getMessage());
            return;
        }
        fail();
    }

    @ParameterizedTest
    @ValueSource(strings = {" leadingSpace", "middle space", "numbers123", "numbersand spaces123"})
    @DisplayName("Throw a validation exception due to request containing invalid chars")
    void testInvalidPatterns(String textValue) {
        RequestObject requestObject = new RequestObject();
        requestObject.setTextValue(textValue);
        List<String> patterns = new ArrayList<>();
        patterns.add("\\s|\\d");
        when(patternConfig.getPatterns()).thenReturn(patterns);
        try {
            requestValidator.isValid(requestObject);
        } catch (ValidationException ex) {
            assertTrue(true);
            assertEquals("Request textValue contains invalid characters", ex.getMessage());
            return;
        }
        fail();
    }


}