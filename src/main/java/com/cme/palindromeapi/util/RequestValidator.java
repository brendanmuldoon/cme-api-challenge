package com.cme.palindromeapi.util;

import com.cme.palindromeapi.model.RequestObject;

public interface RequestValidator {

    void isValid(RequestObject request);

}
