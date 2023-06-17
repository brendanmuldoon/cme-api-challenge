package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.StoredTextValue;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheServiceImpl implements CacheService {

    private final Map<String, Boolean> textValueMap = new ConcurrentHashMap<>();

    @Override
    public StoredTextValue findByKey(String key) {

        Boolean value = textValueMap.get(key);

        if(value!=null) {
            StoredTextValue storedTextValue = new StoredTextValue();
            storedTextValue.setTextValue(key);
            storedTextValue.setPalindrome(value);
            return storedTextValue;
        }

        return null;
    }

    @Override
    public void storeProcessedValue(StoredTextValue storedTextValue) {
        textValueMap.put(storedTextValue.getTextValue(), storedTextValue.isPalindrome());
    }
}
