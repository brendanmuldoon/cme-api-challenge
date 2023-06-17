package com.cme.palindromeapi.handler;

import com.cme.palindromeapi.exception.DataStorageException;
import com.cme.palindromeapi.exception.GenericException;
import com.cme.palindromeapi.exception.ValidationException;
import com.cme.palindromeapi.model.ResponseObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PalindromeExceptionHandlerTest {

    private final PalindromeExceptionHandler handler = new PalindromeExceptionHandler();

    @Test
    @DisplayName("Given an 'Exception' returns HTTP 500")
    void testException() {
        Exception ex = new Exception("msg");
        ResponseObject response = (ResponseObject)handler.handleException(ex).getBody();
        testAssertions(response, HttpStatus.INTERNAL_SERVER_ERROR, 500, "Error : msg");
    }

    @Test
    @DisplayName("Given a 'ValidationException' returns HTTP 400")
    void testValidationException() {
        ValidationException ex = new ValidationException("msg");
        ResponseObject response = (ResponseObject)handler.handleValidationException(ex).getBody();
        testAssertions(response, HttpStatus.BAD_REQUEST, 400, "Validation error : msg");
    }

    @Test
    @DisplayName("Given a 'DataStorageException' returns HTTP 500")
    void testDataStorageException() {
        DataStorageException ex = new DataStorageException("msg");
        ResponseObject response = (ResponseObject)handler.handleDataStorageException(ex).getBody();
        testAssertions(response, HttpStatus.INTERNAL_SERVER_ERROR, 500, "DataStorageException error : msg");
    }

    @Test
    @DisplayName("Given a 'GenericException' returns HTTP 500")
    void testGenericException() {
        GenericException ex = new GenericException("msg");
        ResponseObject response = (ResponseObject)handler.handleGenericException(ex).getBody();
        testAssertions(response, HttpStatus.INTERNAL_SERVER_ERROR, 500, "GenericException error : msg");
    }

    private void testAssertions(ResponseObject responseObject,
                                HttpStatus expectedHttpStatus,
                                int expectedCode,
                                String expectedMsg) {
        assertNotNull(responseObject);
        assertEquals(expectedHttpStatus, responseObject.getHttpStatus());
        assertEquals(expectedCode, responseObject.getCode());
        assertEquals(expectedMsg, responseObject.getMessage());
    }

}