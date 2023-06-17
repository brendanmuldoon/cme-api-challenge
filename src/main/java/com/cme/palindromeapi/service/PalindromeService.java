package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PalindromeService {
    ResponseObject checkIsPalindrome(RequestObject request) throws JsonProcessingException;
}
