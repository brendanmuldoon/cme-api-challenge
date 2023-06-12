package com.cme.palindromeapi.controller;

import com.cme.palindromeapi.model.RequestObject;
import com.cme.palindromeapi.model.ResponseObject;
import com.cme.palindromeapi.service.PalindromeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PalindromeController {

    private final PalindromeService service;

    public PalindromeController(PalindromeService service) {
        this.service=service;
    }

    @PostMapping(value = "/api/isPalindrome", produces = { "application/json;charset=utf-8" }, consumes = {"application/json;charset=utf-8" })
    public ResponseEntity<String> isPalindrome(
            @RequestBody final RequestObject request) {

        ResponseObject responseObject = service.checkIsPalindrome(request);

        return new ResponseEntity<>(responseObject.getData(), responseObject.getHttpStatus());

    }


}
