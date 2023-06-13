package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;
import com.cme.palindromeapi.util.PalindromeConstants;
import com.cme.palindromeapi.util.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PalindromeServiceImpl implements PalindromeService {

    private final RequestValidator requestValidator;

    public PalindromeServiceImpl(RequestValidator requestValidator) {
        this.requestValidator = requestValidator;

    }

    @Override
    public ResponseObject checkIsPalindrome(RequestObject request) { // add logging
        log.info("Checking if request is valid...");
        requestValidator.isValid(request);

        ResponseObject response = new ResponseObject();
        response.setCode(200);
        response.setData("ok");
        response.setHttpStatus(HttpStatus.OK);


        return response;
    }
}
