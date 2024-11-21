package com.ecommerce.emailservice.service.impl;

import com.ecommerce.emailservice.dto.EmailRequest;
import com.ecommerce.emailservice.service.SendEmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("javaEmailService")
public class SendEmailJavaSenderServiceImpl implements SendEmailService {
    @Override
    public void sendEmailWithText(EmailRequest emailRequest) {

    }

    @Override
    public void sendHtmlEmail(EmailRequest emailRequest) {

    }
}
