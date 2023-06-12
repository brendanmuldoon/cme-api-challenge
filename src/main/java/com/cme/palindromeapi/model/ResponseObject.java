package com.cme.palindromeapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseObject {

    private String data;

    private String code;
    private String reason;
    private String message;
    private HttpStatus httpStatus;
}
