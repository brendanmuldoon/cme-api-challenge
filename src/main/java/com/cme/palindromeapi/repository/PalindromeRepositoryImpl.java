package com.cme.palindromeapi.repository;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PalindromeRepositoryImpl implements PalindromeRepository {

    private final Map<String, Boolean> textValueMap = new ConcurrentHashMap<>();

    private final CacheManager cacheManager;

    public PalindromeRepositoryImpl(CacheManager cacheManager) {
        this.cacheManager=cacheManager;
    }

//    @PostConstruct
//    public void init() {
//        update();
//    }
//
//    private void update() {
//        for (var v : readAll()) {
//            cacheManager.getCache("books").put(book.getIsbn(), book);
//        }
//    }

    @Override
    public void write(String textValue, boolean textValueIsPalindrome) {
        textValueMap.put(textValue, textValueIsPalindrome);
    }

    @Override
    @Cacheable("textValues")
    public boolean readValue(String textValue) {
        return textValueMap.get(textValue);
    }

    @Override
    public Boolean doesContainTextValue(String textValue) {
        return textValueMap.containsKey(textValue);
    }
}
