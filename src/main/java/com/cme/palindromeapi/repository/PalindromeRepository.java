package com.cme.palindromeapi.repository;

import java.util.List;

public interface PalindromeRepository {

    void write(String message);

    List<String> readAll();
}
