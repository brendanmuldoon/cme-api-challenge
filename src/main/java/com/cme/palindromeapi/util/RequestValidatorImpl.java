package com.cme.palindromeapi.util;

import com.cme.palindromeapi.config.PatternConfig;
import com.cme.palindromeapi.exception.ValidationException;
import com.cme.palindromeapi.model.RequestObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
@Slf4j
public class RequestValidatorImpl implements RequestValidator {

    private final PatternConfig patternConfig;

    public RequestValidatorImpl(PatternConfig patternConfig) {
        this.patternConfig=patternConfig;
    }

    public void isValid(RequestObject request) {
        log.info("Validating request...");
        isRequestNull(request);
        isTextValueNullOrBlank(request);
        doesContainInvalidChars(request, patternConfig.getPatterns());
        log.info("Request is valid...");
    }

    private void isRequestNull(RequestObject request) {
        if(request == null) {
            throw new ValidationException("Request cannot be null");
        }
    }

    private void doesContainInvalidChars(RequestObject request, List<String> patterns) {
        for(String pattern : patterns) {
            Pattern p = Pattern.compile(pattern);
            if(p.matcher(request.getTextValue()).find()) {
                throw new ValidationException("Request textValue contains invalid characters");
            }
        }
    }

    private void isTextValueNullOrBlank(RequestObject request) {
        isNull(request);
        isBlank(request);
    }

    private void isBlank(RequestObject request) {
        if(request.getTextValue().isBlank()) {
            throw new ValidationException("Request textValue cannot be blank");
        }
    }

    private void isNull(RequestObject request) {
        if(request.getTextValue()==null) {
            throw new ValidationException("Request textValue cannot be null");
        }
    }
}
