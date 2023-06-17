package com.cme.palindromeapi.controller;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;
import com.cme.palindromeapi.service.PalindromeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PalindromeController {

    private final PalindromeService service;

    public PalindromeController(PalindromeService service) {
        this.service=service;
    }

    @PostMapping(value = "/api/isPalindrome", produces = { "application/json;charset=utf-8" }, consumes = {"application/json;charset=utf-8" })
    public ResponseEntity<ResponseObject> isPalindrome(
            @RequestBody final RequestObject request) {

        log.info("Received request : {}", request);

        ResponseObject responseObject = service.checkIsPalindrome(request);

        return new ResponseEntity<>(responseObject, responseObject.getHttpStatus());

//        if(storedTextValue.isPalindrome()) {
//            return new ResponseEntity<>("IS A PALINDROME", HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>("IS NOT A PALINDROME", HttpStatus.BAD_REQUEST);

    }


}
