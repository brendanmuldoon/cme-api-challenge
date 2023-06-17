package com.cme.palindromeapi.repository;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PalindromeFileRepositoryImpl implements PalindromeRepository {

    private final CacheManager cacheManager;

    public PalindromeFileRepositoryImpl(CacheManager cacheManager) {
        this.cacheManager=cacheManager;
    }


    @Override
    public void write(String textValue, boolean textValueIsPalindrome) {
//        textValueMap.put(textValue, textValueIsPalindrome);
    }

    @Override
    @Cacheable("textValues")
    public Boolean readValue(String textValue) {
        return null;
    }

    @Override
    public Boolean doesContainTextValue(String textValue) {
        return null;
    }
}
