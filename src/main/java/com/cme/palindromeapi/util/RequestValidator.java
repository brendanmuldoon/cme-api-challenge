package com.cme.palindromeapi.util;

import com.cme.palindromeapi.model.RequestObject;

import java.util.regex.Pattern;

public interface RequestValidator {

    void isValid(RequestObject request);

}
