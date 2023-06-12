package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;

public interface PalindromeService {
    ResponseObject checkIsPalindrome(RequestObject request);
}
