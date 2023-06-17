package com.cme.palindromeapi.publisher;

import com.cme.palindromeapi.model.StoredTextValue;

public interface Publisher {

    void sendMessage(StoredTextValue storedTextValue);
}
