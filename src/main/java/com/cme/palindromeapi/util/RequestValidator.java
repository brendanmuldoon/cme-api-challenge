package com.cme.palindromeapi.util;

import com.cme.palindromeapi.exception.ValidationException;
import com.cme.palindromeapi.model.RequestObject;

import static com.cme.palindromeapi.util.PalindromeConstants.INVALID_PATTERN;

public class RequestValidator {

    private RequestValidator(){}

    public static void isValid(RequestObject request) {

        isRequestNull(request);
        isTextValueMissing(request);
        isBlank(request);
        doesContainInvalidChars(request);

    }

    private static void isRequestNull(RequestObject request) {
        if(request==null) {
            throw new ValidationException("Request cannot be null");
        }
    }

    private static void doesContainInvalidChars(RequestObject request) {
        if(INVALID_PATTERN.matcher(request.getTextValue()).find()) {
            throw new ValidationException("Request textValue contains invalid characters");
        }
    }

    private static void isTextValueMissing(RequestObject request) {
        isNull(request);
        isBlank(request);
    }

    private static void isBlank(RequestObject request) {
        if(request.getTextValue().isBlank()) {
            throw new ValidationException("Request textValue cannot be blank");
        }
    }

    private static void isNull(RequestObject request) {
        if(request.getTextValue()==null) {
            throw new ValidationException("Request textValue cannot be null");
        }
    }
}
