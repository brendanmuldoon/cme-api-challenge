package com.cme.palindromeapi.service;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;
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

    public PalindromeServiceImpl(RequestValidator requestValidator, PalindromeRepository palindromeRepository) {
        this.requestValidator = requestValidator;
        this.palindromeRepository = palindromeRepository;
    }

    @Override
    public ResponseObject checkIsPalindrome(RequestObject request) { // add logging
        log.info("Checking if request is valid...");
        requestValidator.isValid(request);
        log.info("Request is valid... Proceeding to check if {} is in the cache...", request.getTextValue());

        if(!existsInCache(request.getTextValue())) {
            log.info("{} is not in the cache... adding now...", request.getTextValue());
            boolean textValueIsPalindrome = isPalindrome(request.getTextValue().toLowerCase());
            palindromeRepository.write(request.getTextValue(), textValueIsPalindrome);

            if(textValueIsPalindrome) {
                log.info("Confirmed, {} is a palindrome... ", request.getTextValue());
                return generateResponse(String.format("%s is a palindrome", request.getTextValue()));
            } else {
                log.info("Confirmed, {} is not a palindrome...", request.getTextValue());
                return generateResponse(String.format("%s is not a palindrome", request.getTextValue()));
            }
        }
        log.info("{} is in the cache already...", request.getTextValue());

        if(palindromeRepository.readValue(request.getTextValue())){
            log.info("Confirmed, {} is a palindrome...", request.getTextValue());

            return generateResponse(String.format("%s is a palindrome", request.getTextValue()));
        }
        log.info("Confirmed, {} is not a palindrome...", request.getTextValue());
        return generateResponse(String.format("%s is not a palindrome", request.getTextValue()));
    }

    private boolean existsInCache(String textValue) {
        return palindromeRepository.doesContainTextValue(textValue);
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
