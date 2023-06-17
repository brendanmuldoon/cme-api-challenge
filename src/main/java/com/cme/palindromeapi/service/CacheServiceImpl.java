package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.StoredTextValue;
import com.cme.palindromeapi.repository.PalindromeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

    private final Map<String, Boolean> textValueMap;

    private final PalindromeRepository palindromeRepository;

    private final ObjectMapper mapper;

    public CacheServiceImpl(PalindromeRepository palindromeRepository,
                            ObjectMapper mapper,
                            ConcurrentMap<String, Boolean> textValueMap) {
        this.textValueMap = textValueMap;
        this.palindromeRepository  = palindromeRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    private void loadValuesIntoCache() {
        List<String> data = palindromeRepository.readAll();
        int count = 0;
        for(String value : data) {
            try {
                StoredTextValue storedTextValue = mapper.readValue(value, StoredTextValue.class);
                textValueMap.put(storedTextValue.getTextValue(), storedTextValue.isPalindrome());
            } catch (JsonProcessingException ex) {
                log.error("Error mapping data to object : Exception : {}", ex.getMessage());
                count++;
            }
        }

        if(count>0) {
            log.error("Error loading {} objects from the data storage on startup", count);

        }
        log.info("Database values loaded into cache");
    }


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
