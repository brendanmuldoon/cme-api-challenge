package com.cme.palindromeapi.listener;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Listener {

    void retrieveMessage(String message) throws JsonProcessingException;
}
