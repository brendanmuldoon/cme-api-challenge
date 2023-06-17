package com.cme.palindromeapi.repository;

public interface PalindromeRepository {

    void write(String textValue, boolean textValueIsPalindrome);

    Boolean readValue(String textValue);

    Boolean doesContainTextValue(String textValue);
}
