package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;
import com.cme.palindromeapi.model.StoredTextValue;

public interface PalindromeService {
    ResponseObject checkIsPalindrome(RequestObject request);
}
