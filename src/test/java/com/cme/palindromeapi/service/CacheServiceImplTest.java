package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.StoredTextValue;
import com.cme.palindromeapi.repository.PalindromeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CacheServiceImplTest {

    @Mock
    private PalindromeRepository palindromeRepository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private ConcurrentMap<String, Boolean> textValueMap;

    @InjectMocks
    private CacheServiceImpl cacheService;

    @BeforeEach
    public void init() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        MockitoAnnotations.openMocks(this);
        when(palindromeRepository.readAll()).thenReturn(generateList());
        when(mapper.readValue(anyString(), eq(StoredTextValue.class))).thenReturn(new StoredTextValue("test", true));
        when(textValueMap.put(anyString(), anyBoolean())).thenReturn(true);
        //call post-constructor
        Method postConstruct =  CacheServiceImpl.class.getDeclaredMethod("loadValuesIntoCache",null);
        postConstruct.setAccessible(true);
        postConstruct.invoke(cacheService);
    }

    @Test
    @DisplayName("When given a key return valid storedTextValue")
    void testFindValidStoredTextValueSuccess() {
        String key = "mom";
        when(textValueMap.get(key)).thenReturn(true);
        StoredTextValue result = cacheService.findByKey(key);
        assertNotNull(result);
        assertEquals("mom", result.getTextValue());
        assertTrue(result.isPalindrome());
    }

    @Test
    @DisplayName("When given a key return null")
    void testFindValidStoredTextValueNull() {
        String key = "mom";
        when(textValueMap.get(key)).thenReturn(null);
        StoredTextValue result = cacheService.findByKey(key);
        assertNull(result);

    }

    @Test
    @DisplayName("When given a storedTextValue then store in map")
    void testPutObjectInMap() {
        StoredTextValue storedTextValue = new StoredTextValue("test", false);
        cacheService.storeProcessedValue(storedTextValue);
        verify(textValueMap, times(2)).put(anyString(), anyBoolean()); // once on load
    }


    private List<String> generateList() throws IOException {
        final String filePath = "classpath:data/mock-processed-values-db.txt";
        final String data = Files.readString(Paths.get(ResourceUtils.getFile(filePath).toURI()));
        List<String> splitData = Lists.newArrayList(data.split("\n"));
        return new ArrayList<>(splitData);
    }

}