package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.StoredTextValue;

public interface CacheService {

    StoredTextValue findByKey(String key);

    void storeProcessedValue(StoredTextValue storedTextValue);
}
