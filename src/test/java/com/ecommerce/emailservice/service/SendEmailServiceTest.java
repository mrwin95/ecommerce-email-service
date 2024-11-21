package com.ecommerce.emailservice.service;

import com.ecommerce.emailservice.dto.EmailRequest;
import com.ecommerce.emailservice.service.impl.SendEmailSESServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SendEmailServiceTest {

    private JavaMailSender mockJavaMailSender;

    @Mock
    private SesClient sesClient;

    @InjectMocks SendEmailService sendEmailService = new SendEmailSESServiceImpl(sesClient);
    private  EmailRequest emailRequest;
    @BeforeEach
    public void setUp(){
        emailRequest  = new EmailRequest("mrwin95@gmail.com", "wincadevops@gmail.com", "test subject", "test body");
        // Mock JavaMailSender
        mockJavaMailSender = Mockito.mock(JavaMailSender.class);
        // initialize SendEmailService
        sendEmailService = new SendEmailSESServiceImpl(sesClient);
    }

    @Test
    public void testSendEmailWithText_Success() throws Exception {
        // Call the method

        SendEmailRequest expectedRequest = SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(emailRequest.getTo()).build())
                .message(Message.builder().subject(Content.builder().data(emailRequest.getSubject()).build()).body(Body.builder().text(Content.builder().data(emailRequest.getBody()).build()).build()).build())
                .source(emailRequest.getFrom())
                .build();

        Mockito.when(sesClient.sendEmail(Mockito.eq(expectedRequest))).thenReturn(SendEmailResponse.builder().build());
        // Act

        sendEmailService.sendEmailWithText(emailRequest);
        // Mock Assert

        Mockito.verify(sesClient, Mockito.times(1)).sendEmail(Mockito.any(SendEmailRequest.class));
    }

    @Test
    public void testSendEmailWithText_Failure() throws Exception {
        // Simulate an exception when calling the send method
        Mockito.when(sesClient.sendEmail(Mockito.any(SendEmailRequest.class)))
                .thenThrow(RuntimeException.class);

        // Act & Assert

        Assertions.assertThrows(RuntimeException.class, () -> {
            sendEmailService.sendEmailWithText(emailRequest);
        });

        Mockito.verify(sesClient, Mockito.times(1)).sendEmail(Mockito.any(SendEmailRequest.class));
    }
}
