package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;
import com.cme.palindromeapi.model.StoredTextValue;
import com.cme.palindromeapi.publisher.Publisher;
import com.cme.palindromeapi.repository.PalindromeRepository;
import com.cme.palindromeapi.util.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PalindromeServiceImpl implements PalindromeService {

    private final RequestValidator requestValidator;
    private final PalindromeRepository palindromeRepository;
    private final CacheService cacheService;
    private final Publisher publisher;

    public PalindromeServiceImpl(RequestValidator requestValidator,
                                 PalindromeRepository palindromeRepository,
                                 CacheService cacheService,
                                 Publisher publisher) {
        this.requestValidator = requestValidator;
        this.palindromeRepository = palindromeRepository;
        this.cacheService = cacheService;
        this.publisher = publisher;
    }

    @Override
    public ResponseObject checkIsPalindrome(RequestObject request) { // add logging
        log.info("Validating request...");
        requestValidator.isValid(request);
        log.info("Request is valid... Proceeding to check if {} is in the cache...", request.getTextValue());

        StoredTextValue cachedValue = cacheService.findByKey(request.getTextValue());

        if(cachedValue==null) {
            log.info("Adding new value value...");
            boolean textValueIsPalindrome = isPalindrome(request.getTextValue().toLowerCase());
            StoredTextValue storedTextValue = generateStoredTextValue(request.getTextValue(), textValueIsPalindrome);
            cacheService.storeProcessedValue(storedTextValue);
            publisher.sendMessage(storedTextValue);
            return generateResponse(String.format("isPalindrome: %s", textValueIsPalindrome));

        }
        log.info("Returning existing value...");
        return generateResponse(String.format("isPalindrome: %s", cachedValue.isPalindrome()));
    }

    private StoredTextValue generateStoredTextValue(String textValue, boolean textValueIsPalindrome) {
        return new StoredTextValue(textValue, textValueIsPalindrome);
    }

    private ResponseObject generateResponse(String data) {
        ResponseObject response = new ResponseObject();
        response.setCode(200);
        response.setData(data);
        response.setHttpStatus(HttpStatus.OK);
        return response;
    }

    private boolean isPalindrome(String textValue) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(textValue).reverse();
        return stringBuilder.toString().equals(textValue);
    }
}
