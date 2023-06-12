package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;
import com.cme.palindromeapi.util.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PalindromeServiceImpl implements PalindromeService {

    @Override
    public ResponseObject checkIsPalindrome(RequestObject request) { // add logging
        RequestValidator.isValid(request);
        ResponseObject response = new ResponseObject();
        response.setHttpStatus(HttpStatus.OK);
        response.setData("VALID INPUT");
        response.setCode(HttpStatus.OK.value());
        return response;
    }
}
