package com.ecommerce.emailservice.service;

import com.ecommerce.emailservice.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import java.io.IOException;


public interface SendEmailService {
    public void sendEmailWithText(EmailRequest emailRequest);
    public void sendHtmlEmail(EmailRequest emailRequest) throws IOException;

}
