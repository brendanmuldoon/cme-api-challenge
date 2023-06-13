package com.cme.palindromeapi.repository;

public interface PalindromeRepository {

    void write(String textValue, boolean textValueIsPalindrome);

    boolean readValue(String textValue);

    Boolean doesContainTextValue(String textValue);
}
