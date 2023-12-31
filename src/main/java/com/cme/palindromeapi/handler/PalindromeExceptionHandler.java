package com.cme.palindromeapi.handler;

import com.cme.palindromeapi.exception.DataStorageException;
import com.cme.palindromeapi.exception.GenericException;
import com.cme.palindromeapi.exception.ValidationException;
import com.cme.palindromeapi.model.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PalindromeExceptionHandler {

    //private PalindromeExceptionHandler(){}

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        String msg = String.format("Error : %s", ex.getMessage());
        log.error(msg);
        return generateResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        String msg = String.format("Validation error : %s", ex.getMessage());
        log.error(msg);
        return generateResponseEntity(msg, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataStorageException.class})
    public ResponseEntity<Object> handleDataStorageException(DataStorageException ex) {
        String msg = String.format("DataStorageException error : %s", ex.getMessage());
        log.error(msg);
        return generateResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({GenericException.class})
    public ResponseEntity<Object> handleGenericException(GenericException ex) {
        String msg = String.format("GenericException error : %s", ex.getMessage());
        log.error(msg);
        return generateResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> generateResponseEntity(String msg, int code, HttpStatus httpStatus) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setHttpStatus(httpStatus);
        responseObject.setCode(code);
        responseObject.setMessage(msg);
        return new ResponseEntity<>(responseObject, httpStatus);
    }
}
