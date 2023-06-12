package com.cme.palindromeapi.controller;

import com.cme.palindromeapi.model.ResponseObject;
import com.cme.palindromeapi.service.PalindromeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;

import static com.cme.palindromeapi.utils.Constants.IS_NOT_A_PALINDROME_MSG;
import static com.cme.palindromeapi.utils.Constants.IS_PALINDROME_MSG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class PalindromeControllerTest {

    @InjectMocks
    private PalindromeController palindromeController;

    @MockBean
    private PalindromeService service;

    @Mock
    private ResponseObject responseObject;

    @Autowired
    private MockMvc mockMvc;

    private static final String URL_TEMPLATE = "/api/isPalindrome";

    private static final String REQUEST_OBJECT_FILE_PATH = "classpath:data/RequestObject.json";

    @Test
    @DisplayName("Given valid request object then return is palindrome")
    void testWithValidTextValueIsPalindromeSuccess() throws Exception {

        setUpMocks(String.format(IS_PALINDROME_MSG, "kayak"));

        final RequestBuilder requestBuilder = performRequest();

        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("'kayak' is a palindrome", response.getContentAsString());

    }


    @Test
    @DisplayName("Given valid request object then return is not a palindrome")
    void testWithValidTextValueIsNotPalindromeSuccess() throws Exception {

        setUpMocks(String.format(IS_NOT_A_PALINDROME_MSG, "test"));

        final RequestBuilder requestBuilder = performRequest();

        final MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("'test' is not a palindrome", response.getContentAsString());

    }

    private void setUpMocks(String data) {
        when(service.checkIsPalindrome(any())).thenReturn(responseObject);
        when(responseObject.getData()).thenReturn(data);
        when(responseObject.getHttpStatus()).thenReturn(HttpStatus.OK);

    }

    private RequestBuilder performRequest() throws IOException {
        return MockMvcRequestBuilders.post
                        (URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Files.readAllBytes(ResourceUtils.getFile(REQUEST_OBJECT_FILE_PATH).toPath()));
    }

}