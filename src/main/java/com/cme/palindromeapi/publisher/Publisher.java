package com.cme.palindromeapi.publisher;

import com.cme.palindromeapi.model.StoredTextValue;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface Publisher {

    void sendMessage(StoredTextValue storedTextValue) throws JsonProcessingException;
}
