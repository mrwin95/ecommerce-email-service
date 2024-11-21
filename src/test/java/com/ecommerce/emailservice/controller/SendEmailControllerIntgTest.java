package com.ecommerce.emailservice.controller;

import com.ecommerce.emailservice.dto.EmailRequest;
import com.ecommerce.emailservice.service.SendEmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SendEmailController.class)
public class SendEmailControllerIntgTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SendEmailService sendEmailService;
    private EmailRequest validEmailRequest;
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();
        validEmailRequest = new EmailRequest();
        validEmailRequest.setTo("wincadevops@gmail.com");
        validEmailRequest.setSubject("test subject");
        validEmailRequest.setBody("Test body");
        validEmailRequest.setFrom("mrwin95@gmail.com");
    }
    @Test
    public void testSendEmailText_Success() throws Exception {

        doNothing()
                .when(sendEmailService).sendEmailWithText(any(EmailRequest.class));
        mockMvc.perform(post("/api/v1/email/send-text")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validEmailRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Text Email sent successfully"));

        verify(sendEmailService, times(1)).sendEmailWithText(any(EmailRequest.class));
    }

    @Test
    public void testSendEmailText_Failure() throws Exception {
        doThrow(new RuntimeException("")).when(sendEmailService).sendEmailWithText(any(EmailRequest.class));
        mockMvc.perform(post("/api/v1/email/send-text")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validEmailRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failure to send email: "));

        verify(sendEmailService, times(1)).sendEmailWithText(any(EmailRequest.class));
    }
}
