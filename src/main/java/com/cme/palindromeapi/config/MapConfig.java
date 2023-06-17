package com.cme.palindromeapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Configuration
public class MapConfig {

    @Bean
    public ConcurrentMap<String, Boolean> concurrentMap() {
        return new ConcurrentHashMap<>();

    }
}
